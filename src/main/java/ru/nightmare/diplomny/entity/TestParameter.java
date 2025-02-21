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
    private Integer testParameterID;
    private Integer testID;
    private String name;
    private Integer required;
    private Integer previousRequired;
}
