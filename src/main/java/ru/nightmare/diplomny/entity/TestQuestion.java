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
    private int testQuestionId;
    private int numberInOrder;
    private int testId;
    private String question;
    private int showAnswersPerInstance;
    private int timeForAnsweringInSec;
    private Date taken;
    private Date answered;
}
