package ru.nightmare.diplomny.entity;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TestAnswerReward {
    private int testAnswerRewardId;
    private int testAnswerId;
    private int parameterId;
    private int value;
}
