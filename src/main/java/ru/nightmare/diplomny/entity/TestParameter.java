package ru.nightmare.diplomny.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TestParameter {
    @Id
    private int testParameterID;
    private int testID;
    private String name;
    private int required;
    private int previousRequired;
}
