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
public class TestQuestion {
    @Id
    private Integer testQuestionID;
    private Integer numberInOrder;
    private Integer testID;
    private String question;
    private Integer showAnswersPerInstance;
    private Integer timeForAnsweringInSec;
}
