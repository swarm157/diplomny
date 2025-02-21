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
    Set<TestAnswer> testAnswer;
    @MappedCollection(idColumn = "test_id", keyColumn = "redirected_to_number")
    Set<TestInstanceRedirection> redirection;
}
