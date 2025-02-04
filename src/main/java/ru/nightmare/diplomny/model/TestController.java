package ru.nightmare.diplomny.model;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nightmare.diplomny.entity.*;

import java.util.List;

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
    class Test {
        Integer id;
        String name;
        String description;
        Boolean passed;
        Boolean allowedToPass;
        Boolean needMoreFromPrevious;
    }

    @AllArgsConstructor
    class Question {
        Integer id;
        Integer testID;
        String text;
        List<String> answers;

    }
    public String questionToJson(TestQuestion question, User user) {
        return gson.toJson(new Question(question.getTestQuestionID(), question.getTestID(), question.getQuestion(), dbController.getAnswers(question, user)));
    }
    public String testToJson(User user, ru.nightmare.diplomny.entity.Test test) {
        TestProcessor tp = dbController.testFor(user, test);
        return gson.toJson(new Test(test.getTestID(), test.getName(), test.getDescription(), tp.isPassed(), tp.isAllowedToPass(), tp.getFailedByPreviousTestLowResults()));
    }

    public String resultToJson() {

    }
    public void generateTestUsersFor(int userID) {}
    public void generateRedirectionFor(int userID) {}
    public void revalidateTestTree() {}

}
