package ru.nightmare.diplomny.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.Set;

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
    @MappedCollection(idColumn = "test_parameter_id")
    Set<TestParameter> testParameter;
}
