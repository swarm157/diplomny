package ru.nightmare.diplomny.entity;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TestAnswer {
    private int testAnswer;
    private int testId;
    private String answer;
}
