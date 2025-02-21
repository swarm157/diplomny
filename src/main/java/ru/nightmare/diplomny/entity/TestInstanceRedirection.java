package ru.nightmare.diplomny.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TestInstanceRedirection {
    @Id
    private int testInstanceRedirectionID;
    private int testQuestionID;
    private int testUserID;
    private int testAnswerID;
    private int redirectedToNumber;
}
