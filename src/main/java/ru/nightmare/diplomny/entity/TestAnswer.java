package ru.nightmare.diplomny.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

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
}
