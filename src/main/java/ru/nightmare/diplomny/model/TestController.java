package ru.nightmare.diplomny.model;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Component;
import ru.nightmare.diplomny.entity.*;
import ru.nightmare.diplomny.repository.TestUserAnswerRepository;
import ru.nightmare.diplomny.service.*;

import javax.sql.DataSource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;

@Component
/*
 Данный класс будет ближе к серверу нежели модели
 Нужно чтобы он минимум или напрямую не обращаясь к серверу делал, всё, что требуется с тестами, все главные остальные операции которые
 не делает TestProcessor.
 Это нужно чтобы уже сами веб контроллеры не были нагружены лишней логикой, тобишь вся логика не касающаяся взаимодействия пользователя
 и сайта должна оказаться здесь.
 Данная логика это:
 ---Генерация json для куяска;
 ---Контроль за обращениями к тесту за базой данных
 ---Валидация и слежка за свежестью состояния всех рассчётов
*/
public class TestController {
    private Logger log = LoggerFactory.getLogger(TestController.class.getName());
    @Autowired
    DBController dbController;
    @Autowired
    DataSource source;

    @Autowired
    TestAnswerRewardService testAnswerRewardService;
    @Autowired
    TestAnswerService testAnswerService;
    @Autowired
    TestQuestionService testQuestionService;
    @Autowired
    TestInstanceRedirectionService testInstanceRedirectionService;
    @Autowired
    UserService userService;
    @Autowired
    TestService testService;
    @Autowired
    TestUserService testUserService;
    @Autowired
    TestResultService testResultService;
    @Autowired
    TestUserAnswerService testUserAnswerService;
    @Autowired
    TestParameterService testParameterService;
    @Autowired
    UserStateService userStateService;
    @Autowired
    UserPointerService userPointerService;
    @Autowired
    BookService bookService;
    @Autowired
    CategoryService categoryService;
    Gson gson = new Gson();
    @Autowired
    private TestUserAnswerRepository testUserAnswerRepository;

    public User changeUser(User user) {
        userService.updateUser(user.getUserID(), user.getAvatar());
        userService.updateUser(user.getUserID(), user.getDescription());
        return userService.getUser(user.getUserID());
    }

    public UserPointer getPointer(User user) {
        return userPointerService.getUserPointer(user.getUserID());
    }

    public TestUser getTestUser(UserPointer pointer) {
        return testUserService.getTestUser(pointer.getPointer());
    }

    public String getTest(Integer id) {
        return gson.toJson(testService.getTest(id));
    }

    public String getAllTests(User user) throws SQLException {
        ArrayList<Test> tests = new ArrayList<>();
        for (ru.nightmare.diplomny.entity.Test t : testService.getAllTests()) {
            TestProcessor tp = dbController.testFor(user, t);
            tests.add(new Test(t.getTestID(), t.getName(), t.getDescription(), tp.isPassed(), tp.isAllowedToPass(), tp.getFailedByPreviousTestLowResults()));
        }
        return gson.toJson(tests);
    }

    public String beginTesting(User user, Integer testID) throws SQLException {
        generateRedirectionFor(user.getUserID(), testID);
        TestUser tu = testUserService.getTestUserByUserAndTest(user.getUserID(), testID);
        UserPointer pointer = getPointer(user);
        userPointerService.updateUserPointer(pointer.getUserPointerID(), pointer.getUserID(), 2, testID);
        return questionToJson(testQuestionService.getFirstTestQuestion(testID), tu);
    }

    public String endUpTesting(User user) throws SQLException {
        for(TestUserAnswer answer : testUserAnswerService.getAllTestUserAnswers(testUserService.getTestUserByUserAndTest(user.getUserID(), getPointer(user).getPointer()).getTestUserID())) {
            Date date = answer.getAnswered();
            if(date==null) {
                date = new Date(System.currentTimeMillis());
                testUserAnswerService.updateTestUserAnswer(answer.getTestUserAnswerID(), date);
            }
        }
        ru.nightmare.diplomny.entity.Test test = testService.getTest(getPointer(user).getPointer());
        TestProcessor tp = dbController.testFor(user, test);
        TestUser testUser = testUserService.getTestUserByUserAndTest(user.getUserID(), test.getTestID());
        testUserService.updateTestUser(testUser.getTestUserID(), tp.isPassed());
        UserPointer pointer = getPointer(user);
        pointer.setUserStateID(1);
        userPointerService.updateUserPointer(pointer.getUserPointerID(), pointer.getUserID(), pointer.getUserStateID(), pointer.getPointer());
        revalidateTestTree(user);
        return tp.message;
    }

    public TestUser getTestUser(User user, Integer id) {
        return testUserService.getTestUserByUserAndTest(user.getUserID(), id);
    }

    public Integer getTimeUsed(User user, Integer id) {
        long pre = 0L;
        for(TestUserAnswer answer : testUserAnswerService.getAllTestUserAnswers(testUserService.getTestUserByUserAndTest(user.getUserID(), id).getTestUserID())) {
            pre += answer.getAnswered().getTime()-answer.getTaken().getTime();
        }
        return Math.toIntExact(pre / 1000);
    }

    public List<ru.nightmare.diplomny.entity.Test> getAllTests() {
        return (List<ru.nightmare.diplomny.entity.Test>) testService.getAllTests();
    }

    public List<TestAnswer> getAllAnswers(Integer questionID) {
        return (List<TestAnswer>) testAnswerService.getAllTestAnswers(questionID);
    }

    public String getAllQuestions(Integer id) {
        return gson.toJson(testQuestionService.getAllTestQuestions(id));
    }

    public String createReward(Integer id, Integer reward, Integer pid) {
        return gson.toJson(testAnswerRewardService.createTestAnswer(id, reward, pid));
    }

    public String changeReward(Integer id, Integer reward, Integer parameterId, Integer answerId) {
        return gson.toJson(testAnswerRewardService.updateTestAnswerReward(id, answerId, reward, parameterId));
    }

    public String deleteReward(Integer id) {
        testAnswerRewardService.deleteTestAnswerReward(id);
        return "deleted";
    }

    public String getAllRewards(Integer id) {
        return gson.toJson(testAnswerRewardService.getAllTestAnswerRewards(id));
    }

    public String createParameter(String name, Integer required, Integer prevRequired, Integer testId) {
        return gson.toJson(testParameterService.createTestParameter(name, required, prevRequired, testId));
    }

    public String changeParameter(Integer id, String name, Integer required, Integer prevRequired, Integer testId) {
        return gson.toJson(testParameterService.updateTestParameter(id, name, required, prevRequired, testId));
    }

    public String deleteParameter(Integer id) {
        testParameterService.deleteTestParameter(id);
        return "deleted";
    }

    public String getAllParameters(Integer id) {
        return gson.toJson(testParameterService.getTestParameters(id));
    }

    public String createCategory(String name) {
        return gson.toJson(categoryService.createCategory(name));
    }
    public String changeCategory(int id, String name) {
        return gson.toJson(categoryService.updateCategory(id, name));
    }
    public String deleteCategory(int id) {
        categoryService.deleteCategory(id);
        return "deleted";
    }
    public String getAllCategory() {
        return gson.toJson(categoryService.getAllCategories());
    }
    public String createBook(String name, String file, Integer categoryId, Byte[] preview) {
        return gson.toJson(bookService.createBook(name, file, categoryId, preview));
    }
    public String changeBook(Integer id, String name, String file, Integer categoryId, Byte[] preview) {
        return gson.toJson(bookService.updateBook(id, name, file, categoryId, preview));
    }
    public String deleteBook(int id) {
        bookService.deleteBook(id);
        return "deleted";
    }
    public String getAllBooks(int categoryId) {
        return gson.toJson(bookService.getAllBooks(categoryId));
    }
    @AllArgsConstructor
    class Result {
        String message;
        Boolean passed;

        @AllArgsConstructor
        @NoArgsConstructor
        static class Row {
            String name;
            int required;
            int value;
        }
        List<Row> rows;
    }

    @AllArgsConstructor
    static
    class Test {
        Integer id;
        String name;
        String description;
        Boolean passed;
        Boolean allowedToPass;
        Boolean needMoreFromPrevious;
    }

    @AllArgsConstructor
    static
    class Question {
        Integer id;
        Integer testID;
        String text;
        List<String> answers;

    }
    public String questionToJson(TestQuestion question, TestUser user) throws SQLException {
        return gson.toJson(new Question(question.getTestQuestionID(), question.getTestID(), question.getQuestion(), dbController.getAnswers(question, user)));
    }
    public String testToJson(User user, ru.nightmare.diplomny.entity.Test test) throws SQLException {
        TestProcessor tp = dbController.testFor(user, test);
        return gson.toJson(new Test(test.getTestID(), test.getName(), test.getDescription(), tp.isPassed(), tp.isAllowedToPass(), tp.getFailedByPreviousTestLowResults()));
    }

    public String resultToJson(User user, ru.nightmare.diplomny.entity.Test test) throws SQLException {
        TestProcessor tp = dbController.testFor(user, test);

        List<Result.Row> rows = new ArrayList<>();

        List<TestParameter> parameters = new ArrayList<>(test.getTestParameter().stream().toList());

        ru.nightmare.diplomny.entity.Test prev = testService.getTest(test.getPreviousId());

        if(prev!=null) {
            List<TestParameter> old = new ArrayList<>();
            for(TestParameter tpp : parameters) {
                prev.getTestParameter().forEach( oTpp -> {if(oTpp.getName().equals(tpp.getName())) {old.add(oTpp);}});
            }
            parameters.addAll(old);
        }

        for(TestParameter tpp : parameters) {
            Result.Row row = new Result.Row();
            row.name = tpp.getName();
            row.value = 0;
            row.required = tpp.getRequired();
            for(TestResult result: tpp.getTestResult()) {
                if(Objects.equals(result.getTestUserID(), user.getUserID())) {
                    row.value = result.getSummary();
                    break;
                }
            }
            rows.add(row);
        }
        Result result = new Result(tp.message, tp.isPassed(), rows);
        return gson.toJson(result);
    }
    /*
        Нашёл то что не учёл когда писал архитектуру БД, всё бы ничего, но очень тяжело работать(невозможно нормальными средствами) работать без указателя
        Вообщем концепция указателя.
        Управляет положением пользователя в приложении, режет возможность быть там где ему не должно.
        Например, заниматься двумя тестами одновременно, быть несколькими пользователями хоть это и немного другое и снижать нагрузку на сайт
     */
    public String takeUserAnswer(User user, int answer) throws SQLException, NoSuchElementException {
        UserPointer pointer = user.getPointers().stream().toList().get(0);
        TestUser tu = testUserService.getTestUserByUserAndTest(user.getUserID(), pointer.getPointer());

        for(TestInstanceRedirection redirection : testInstanceRedirectionService.getAllTestInstanceRedirection(tu.getTestUserID(), pointer.getQuestion())) {
            if(redirection.getRedirectedToNumber() == answer) {
                //redirection.getTestAnswerID()
                //testUserAnswerService.createTestUserAnswer(tu.getTestUserID()).;
                TestUserAnswer tua = testUserAnswerService.getTestUserAnswerByQuestionID(tu.getTestUserID(), pointer.getQuestion());
                tua.setAnswered(new Date(System.currentTimeMillis()));
                tua.setAnswer(redirection.getTestAnswerID());
                testUserAnswerService.updateTestUserAnswer(tua.getTestUserAnswerID(), tua.getAnswer(), tua.getAnswered());
                break;
            }
        }
        return getNextQuestion(tu);
    }

    public String getUserById(int id) {
        return gson.toJson(userService.getUser(id));
    }
    public String getNextQuestion(TestUser user) throws SQLException, NoSuchElementException {
        return gson.toJson(testQuestionService.getNextTestQuestion(getPointer(userService.getUser(user.getUserID())).getQuestion()));
    }
    public String registerUser(String name, String lastName, String email, String description, String password) throws SQLException, NoSuchElementException, NoSuchAlgorithmException {
        User user = userService.createUser(name, lastName, email, description, hashPassword(password));
        generateTestUsersFor(user.getUserID());
        return gson.toJson(user);
    }
    public String updateDescription(User user, String description) throws SQLException, NoSuchElementException {
        user.setDescription(description);
        return gson.toJson(changeUser(user));
    }
    public String getResult(TestUser user) throws SQLException, NoSuchElementException {
        User u = userService.getUser(user.getUserID());
        ru.nightmare.diplomny.entity.Test test = testService.getTest(user.getTestID());
        return resultToJson(u, test);
    }
    public String loginUser(String email, String password) throws SQLException, NoSuchElementException, NoSuchAlgorithmException {
        User user = userService.getUserByEmail(email);
        if(user!=null) {
            if(hashPassword(password).equals(user.getPassword()))
                return gson.toJson(user);
            else return null;
        }
        return null;
    }
    public String changeDescription(User user, String description) throws SQLException, NoSuchElementException {
        user.setDescription(description);
        return gson.toJson(changeUser(user));
    }
    public String createTest(String name, String description) throws SQLException, NoSuchElementException {
        return gson.toJson(testService.createTest(name, description));
    }
    public String deleteTest(int id) throws SQLException, NoSuchElementException {
        testService.deleteTest(id);
        return "deleted";
    }
    public String changeTest(int id, String name, String description, int prev) throws SQLException, NoSuchElementException {
        return gson.toJson(testService.updateTest(id,name,description,prev));
    }
    public String changeTest(int id, String name, String description) throws SQLException, NoSuchElementException {
        return gson.toJson(testService.updateTest(id,name,description));

    }
    public String createQuestion(int testId) throws SQLException {
        return gson.toJson(testQuestionService.createTestQuestion("unnamed", testId, testQuestionService.getNumOfQuestions(testId)+1, 5));
    }
    public String changeQuestion(int id, String name, int testId, int inOrder, int perInstance) throws SQLException {
        return gson.toJson(testQuestionService.updateTestQuestion(id, name, testId, inOrder, perInstance));
    }
    public String deleteQuestion(int id) throws SQLException, NoSuchElementException {
        testQuestionService.deleteTestQuestion(id);
        return "deleted";
    }
    public TestAnswer createAnswer(int questionId, String name) {
        return testAnswerService.createTestAnswer(name, questionId);
    }
    public TestAnswer changeAnswer(int id, int questionId, String name) {
        return testAnswerService.updateTestAnswer(id, name, questionId);
    }

    public String deleteAnswer(int id) throws SQLException, NoSuchElementException {
        testAnswerService.deleteTestAnswer(id);
        return "deleted";
    }
    public void generateTestUsersFor(int userID) throws SQLException, NoSuchElementException {
        for (ru.nightmare.diplomny.entity.Test test : testService.getAllTests()) {
            TestUser tu = testUserService.createTestUser(test.getTestID(), userID);
            for(TestParameter testParameter : testParameterService.getTestParameters(test.getTestID())) {
                TestResult result = testResultService.createTestResult(testParameter.getName(), tu.getTestUserID(), testParameter.getTestParameterID());
            }
        }
    }
    public void generateRedirectionFor(int userID, Integer testID) throws SQLException, NoSuchElementException {
        for(TestQuestion question : testQuestionService.getAllTestQuestions(testID)) {
            RandomBag bag = new RandomBag(1, question.getShowAnswersPerInstance());
            for (TestAnswer answer : testAnswerService.getAllTestAnswers(question.getTestQuestionID())) {
                testInstanceRedirectionService.deleteTestInstanceRedirectionByAnswer(answer.getTestAnswerID());
                testInstanceRedirectionService.createTestInstanceRedirection(question.getTestQuestionID(), answer.getTestAnswerID(), userID, bag.pull());
            }

        }
    }
    public void revalidateTestTree(User user) throws SQLException, NoSuchElementException {
        for(TestUser tu : testUserService.getAllTestUsers(user.getUserID())) {
            ru.nightmare.diplomny.entity.Test test = testService.getTest(tu.getTestID());
            TestProcessor tp = dbController.testFor(user, test);
            for(TestResult testResult : tp.getResult()) {
                testResultService.updateTestResult(testResult.getTestResultID(), testResult.getSummary());
            }
        }
    }

    static class RandomBag {
        private final List<Integer> bag;
        private final Random random;

        // Constructor to initialize the bag with numbers between min and max
        public RandomBag(int min, int max) {
            bag = new ArrayList<>();
            for (int i = min; i <= max; i++) {
                bag.add(i);
            }
            random = new Random();
            shuffleBag();
        }

        // Shuffle the bag to randomize the order of numbers
        private void shuffleBag() {
            Collections.shuffle(bag, random);
        }

        // Pull a random number from the bag
        public Integer pull() {
            if (bag.isEmpty()) {
                resetBag();
                return null;
            }
            return bag.remove(bag.size() - 1); // Pull the last element
        }

        // Reset the bag when it's empty
        private void resetBag() {
            bag.clear();
            for (int i = 1; i <= 10; i++) { // Example range from 1 to 10
                bag.add(i);
            }
            shuffleBag();
        }
    }

    // Hash a password
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = digest.digest(password.getBytes());

        // Convert byte array to hexadecimal format
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashedBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
