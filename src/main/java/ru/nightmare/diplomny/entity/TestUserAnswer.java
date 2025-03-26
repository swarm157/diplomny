package ru.nightmare.diplomny.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.sql.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString

/*CREATE TABLE TestUserAnswer(
        testUserAnswerID int,
        testUserID int,
        answer int
);*/

public class TestUserAnswer {
    @Id
    private Integer testUserAnswerID;
    private Integer testUserID;
    private Integer testQuestionID;
    private Date taken;
    private Date answered;
    private Integer answer;
}
