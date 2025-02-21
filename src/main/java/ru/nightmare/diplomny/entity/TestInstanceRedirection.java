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
    private Integer testInstanceRedirectionID;
    private Integer testQuestionID;
    private Integer testUserID;
    private Integer testAnswerID;
    private Integer redirectedToNumber;
}
