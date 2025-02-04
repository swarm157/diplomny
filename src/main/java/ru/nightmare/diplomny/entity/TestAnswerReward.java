package ru.nightmare.diplomny.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TestAnswerReward {
    @Id
    private int testAnswerRewardID;
    private int testAnswerID;
    private int parameterID;
    private int value;
}
