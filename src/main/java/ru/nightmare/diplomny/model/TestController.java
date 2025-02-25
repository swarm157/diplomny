package ru.nightmare.diplomny.model;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nightmare.diplomny.entity.*;
import ru.nightmare.diplomny.service.*;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

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
    Gson gson = new Gson();

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
        testUserService.get
        testAnswerService.getTestAnswer()
    }

    public String getUserById(int id) {
        return gson.toJson(userService.getUser(id));
    }
    public String getNextQuestion(TestUser user) throws SQLException, NoSuchElementException {}
    public String getCurrentlyPassingTest(User user) throws SQLException, NoSuchElementException {}
    public String registerUser(String name, String lastName, String email, String description, String password) throws SQLException, NoSuchElementException {}
    public String updateDescription(User user, String description) throws SQLException, NoSuchElementException {}
    public String getResult(TestUser user) throws SQLException, NoSuchElementException {}
    public String loginUser(String email, String password) throws SQLException, NoSuchElementException {}
    public String changeDescription(User user, String description) throws SQLException, NoSuchElementException {}
    public String createTest(String name, String description) throws SQLException, NoSuchElementException {}
    public String deleteTest(int id) throws SQLException, NoSuchElementException {}
    public String createQuestion(int testId) throws SQLException {}
    public String changeQuestion(int id, String name, int testId, int inOrder, int perInstance) throws SQLException {}
    public String deleteQuestion(int id) throws SQLException, NoSuchElementException {}
    public TestAnswer createAnswer(int questionId, String name) {}
    public TestAnswer changeAnswer(int id, int questionId, String name) {}

    public String deleteAnswer(int id) throws SQLException, NoSuchElementException {}
    public String generateTestUsersFor(int userID) throws SQLException, NoSuchElementException {}
    public String generateRedirectionFor(int userID) throws SQLException, NoSuchElementException {}
    public String revalidateTestTree() throws SQLException, NoSuchElementException {}

}
