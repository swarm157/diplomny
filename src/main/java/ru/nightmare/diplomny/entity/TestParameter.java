package ru.nightmare.diplomny.entity;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TestParameter {
    private int testParameterId;
    private int testId;
    private String name;
    private int required;
    private int previousRequired;
}
