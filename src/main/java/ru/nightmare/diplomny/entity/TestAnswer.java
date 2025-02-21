package ru.nightmare.diplomny.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TestAnswer {
    @Id
    private Integer testAnswerID;
    private Integer testQuestionID;
    private String answer;
    @MappedCollection(idColumn = "test_answer_id")
    private transient Set<TestInstanceRedirection> redirection;
    @MappedCollection(idColumn = "test_answer_id")
    private transient Set<TestAnswerReward> testAnswerReward;

    public TestAnswer(Integer testAnswerID, Integer testQuestionID, String answer) {
        this.testAnswerID = testAnswerID;
        this.testQuestionID = testQuestionID;
        this.answer = answer;
    }
}
