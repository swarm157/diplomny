package ru.nightmare.diplomny.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.sql.Date;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TestQuestion {
    @Id
    private Integer testQuestionID;
    private Integer numberInOrder;
    private Integer testID;
    private String question;
    private Integer showAnswersPerInstance;
    private Integer timeForAnsweringInSec;
    @MappedCollection(idColumn = "test_question_id")
    private transient Set<TestAnswer> testAnswer;
    @MappedCollection(idColumn = "test_id", keyColumn = "redirected_to_number")
    private transient Set<TestInstanceRedirection> redirection;

    public TestQuestion(Integer testQuestionID, Integer numberInOrder, Integer testID, String question, Integer showAnswersPerInstance, Integer timeForAnsweringInSec) {
        this.testQuestionID = testQuestionID;
        this.numberInOrder = numberInOrder;
        this.testID = testID;
        this.question = question;
        this.showAnswersPerInstance = showAnswersPerInstance;
        this.timeForAnsweringInSec = timeForAnsweringInSec;
    }
}
