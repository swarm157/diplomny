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
import java.util.List;
import java.util.NoSuchElementException;

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
    Gson gson = new Gson();

    @AllArgsConstructor
    class Result {
        String message;
        Boolean passed;

        @AllArgsConstructor
        class Row {
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

    public String resultToJson() {
        return gson.toJson();
    }
    public String acceptUserAnswer(User user, int answer) throws SQLException, NoSuchElementException {}
    public TestQuestion getNextQuestion(TestUser user) throws SQLException, NoSuchElementException {}
    public TestUser getCurrentlyPassingTest(User user) throws SQLException, NoSuchElementException {}
    public User registerUser(String name, String lastName, String email, String description, String password) throws SQLException, NoSuchElementException {}
    public User updateDescription(User user, String description) throws SQLException, NoSuchElementException {}
    public String getResult(TestUser user) throws SQLException, NoSuchElementException {}
    public User loginUser(String email, String password) throws SQLException, NoSuchElementException {}
    public User changeDescription(User user, String description) throws SQLException, NoSuchElementException {}
    public ru.nightmare.diplomny.entity.Test createTest(String name, String description) throws SQLException, NoSuchElementException {}
    public void deleteTest(int id) throws SQLException, NoSuchElementException {}
    public TestQuestion createQuestion(int testId) throws SQLException {}
    public TestQuestion changeQuestion(int id, String name, int testId, int inOrder, int perInstance) throws SQLException {}
    public void deleteQuestion(int id) throws SQLException, NoSuchElementException {}
    public TestAnswer createAnswer(int questionId, String name) {}
    public TestAnswer changeAnswer(int id, int questionId, String name) {}

    public void deleteAnswer(int id) throws SQLException, NoSuchElementException {}
    public void generateTestUsersFor(int userID) throws SQLException, NoSuchElementException {}
    public void generateRedirectionFor(int userID) throws SQLException, NoSuchElementException {}
    public void revalidateTestTree() throws SQLException, NoSuchElementException {}

}
