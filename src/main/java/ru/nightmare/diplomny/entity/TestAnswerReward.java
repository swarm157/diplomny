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
    private Integer testAnswerRewardID;
    private Integer testAnswerID;
    private Integer parameterID;
    private Integer value;
}
