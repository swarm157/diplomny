package ru.nightmare.diplomny.model;


import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Component;
import ru.nightmare.diplomny.entity.*;
import ru.nightmare.diplomny.service.*;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@NoArgsConstructor
public class DBController {

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


    private Statement getStatement() throws SQLException {
        return source.getConnection().createStatement();
    }
    private PreparedStatement getPrepared(String sql) throws SQLException {
        return source.getConnection().prepareStatement(sql);
    }
    /*
        Так чё мне надо. Мне надо чтобы по айди вопроса
        и айди конкретного проходимого пользователем теста находились вопросы, сгенерированные для него до этого.

     */
    public List<String> getAnswers(TestQuestion question, TestUser user) throws SQLException {

        List<String> answers = new ArrayList<>();

        PreparedStatement prepared = getPrepared(
                "SELECT test_instance_redirection_id, test_answer_id, redirected_to_number" +
                " FROM TestInstanceRedirection" +
                " WHERE test_user_id = ? AND test_question_id = ?");
        prepared.setInt(1, user.getTestUserID());
        prepared.setInt(2, question.getTestQuestionID());

        ResultSet redirection = prepared.executeQuery();

        HashMap<Integer, Integer> redirectionMap = new HashMap<>();
        HashMap<Integer, String> answerMap = new HashMap<>();

        while (redirection.next()) {
            int testInstanceRedirectionId = redirection.getInt("test_instance_redirection_id");
            int testAnswerId = redirection.getInt("test_answer_id");
            int redirectedToNumber = redirection.getInt("redirected_to_number");

            PreparedStatement prepared2 = getPrepared("SELECT answer FROM TestAnswer where id = ?");

            prepared2.setInt(1, testAnswerId);
            ResultSet answer = prepared2.executeQuery();

            if(answer.next()) {
                String answerString = answer.getString("answer");
                answerMap.put(testAnswerId, answerString);
                redirectionMap.put(redirectedToNumber, testAnswerId);
            }
        }
        for (int i = 0; i < redirectionMap.size(); i++) {
            answers.add(answerMap.get(redirectionMap.get(i)));
        }
        return answers;
    }

    /*
    Таке, чё должна делать эта функция, она должна загружать все имеющиеся данные для того чтобы объект сам автомат произвёл
    соответствующие вычисления.
    Следовательно => а чё следовательно, мне надо загрузить всё чё просит объект.
    Тобишь тест.
    Этот и предыдущий.
    Все перенаправления текущего
    Все параметры
    Все награды
    Все вопросы
    Все результаты
    Все предыдущие результаты теста
    Все предыдущие параметры теста


     */
    public TestProcessor testFor(User user, Test test) throws SQLException {
        PreparedStatement testCurrent = getPrepared("SELECT * FROM TestUser WHERE user_id = ? AND test_id = ?");
        testCurrent.setInt(1, user.getUserID());
        testCurrent.setInt(2, test.getTestID());
        ResultSet s = testCurrent.executeQuery();
        TestUser userCurrent = null;
        if(s.next()) {
            userCurrent = new TestUser(s.getInt("test_user_id"), s.getInt("user_id"), s.getInt("test_id"), s.getBoolean("passed"));
        }
        PreparedStatement testPrevious = getPrepared("SELECT * FROM TestUser WHERE user_id = ? AND test_id = ?");
        testPrevious.setInt(1, user.getUserID());
        testPrevious.setInt(2, test.getPreviousId());
        s = testPrevious.executeQuery();
        TestUser userPrevious = null;
        if(s.next()) {
            userPrevious = new TestUser(s.getInt("test_user_id"), s.getInt("user_id"), s.getInt("test_id"), s.getBoolean("passed"));
        }
        PreparedStatement redirection = getPrepared("SELECT * FROM TestRedirection WHERE test_user_id = ?");
        TestInstanceRedirection[] testInstanceRedirection = null;
        if (userCurrent != null) {
            redirection.setInt(1, userCurrent.getTestUserID());
            s = redirection.executeQuery();
            List<TestInstanceRedirection> testInstanceRedirections = new ArrayList<>();
            while (s.next()) {
                testInstanceRedirections.add(new TestInstanceRedirection(s.getInt("test_instance_redirection_id"), s.getInt("test_question_id"),
                        s.getInt("test_user_id"), s.getInt("test_answer_id"), s.getInt("redirected_to_number")));
            }
            testInstanceRedirection = testInstanceRedirections.toArray(new TestInstanceRedirection[0]);
        }
        TestParameter[] testParameters = null;
        PreparedStatement testParameter = getPrepared("SELECT * FROM TestParameter WHERE test_id = ?");
        testParameter.setInt(1, test.getTestID());
        s = testParameter.executeQuery();
        List<TestParameter> parameters = new ArrayList<>();
        while (s.next()) {
            parameters.add(new TestParameter(s.getInt("test_parameter_id"), s.getInt("test_id"), s.getString("name"), s.getInt("required"), s.getInt("previous_required")));
        }
        testParameters = parameters.toArray(new TestParameter[0]);
        TestAnswerReward[] testAnswerRewards = null;
        for (TestParameter parameter : testParameters) {
            PreparedStatement testAnswerReward = getPrepared("SELECT * FROM TestAnswerReward WHERE parameter_id = ?");
            testAnswerReward.setInt(1, parameter.getTestParameterID());
            s = testAnswerReward.executeQuery();
            List<TestAnswerReward> testAnswerRewardList = new ArrayList<>();
            while (s.next()) {
                testAnswerRewardList.add(new TestAnswerReward(s.getInt("test_answer_reward_id"), s.getInt("test_answer_id"), s.getInt("parameter_id"), s.getInt("reward")));
            }
            testAnswerRewards = testAnswerRewardList.toArray(new TestAnswerReward[0]);
        }
        s = smartSelect(TestAnswer.class.getName(), "test_id = ?", test.getTestID());
        TestAnswer[] testAnswers;
        List<TestAnswer> testAnswerList = new ArrayList<>();
        while (s.next()) {
            testAnswerList.add(new TestAnswer(s.getInt("test_answer_id"), s.getInt("test_id"), s.getString("answer")));
        }
        testAnswers = testAnswerList.toArray(new TestAnswer[0]);
        TestResult[] testResults;
        List<TestResult> testResultList = new ArrayList<>();
        if (userCurrent != null) {
            s = smartSelect(TestResult.class.getName(), "test_user_id = ?", userCurrent.getTestUserID());
            while (s.next()) {
                testResultList.add(new TestResult(s.getInt("test_result_id"), s.getInt("test_parameter_id"), s.getInt("test_user_id"), s.getInt("summary")));
            }
        }
        testResults = testResultList.toArray(new TestResult[0]);
        TestResult[] testResultsP;
        List<TestResult> testResultListP = new ArrayList<>();
        if (userPrevious != null) {
            s = smartSelect(TestResult.class.getName(), "test_user_id = ?", userPrevious.getTestUserID());
            while (s.next()) {
                testResultListP.add(new TestResult(s.getInt("test_result_id"), s.getInt("test_parameter_id"), s.getInt("test_user_id"), s.getInt("summary")));
            }
        }
        testResultsP = testResultListP.toArray(new TestResult[0]);
        TestParameter[] testParametersP = null;
        PreparedStatement testParameterP = getPrepared("SELECT * FROM TestParameter WHERE test_id = ?");
        testParameterP.setInt(1, test.getPreviousId());
        s = testParameterP.executeQuery();
        List<TestParameter> parametersP = new ArrayList<>();
        while (s.next()) {
            parametersP.add(new TestParameter(s.getInt("test_parameter_id"), s.getInt("test_id"), s.getString("name"), s.getInt("required"), s.getInt("previous_required")));
        }
        testParametersP = parametersP.toArray(new TestParameter[0]);
        return new TestProcessor(userCurrent, userPrevious, testInstanceRedirection, testParameters,
                testAnswerRewards, testAnswers, testResults, testResultsP, testParametersP,
                null, "", false, false, false);
    }

    private ResultSet smartSelect(String table, String where, int... id) throws SQLException {
        PreparedStatement prepared = getPrepared("SELECT * FROM " + table + " WHERE " + where);
        for (int i = 0; i < id.length; i++) {
            prepared.setInt(i+1, id[i]);
        }
        return prepared.executeQuery();
    }
}
