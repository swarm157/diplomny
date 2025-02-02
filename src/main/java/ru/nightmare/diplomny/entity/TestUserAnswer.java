package ru.nightmare.diplomny.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

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
    private int testUserAnswerID;
    private int testUserID;
    private int answer;
}
