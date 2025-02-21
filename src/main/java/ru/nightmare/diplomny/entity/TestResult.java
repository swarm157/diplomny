package ru.nightmare.diplomny.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString

/*CREATE TABLE TestResult (
        testResultID int,
        testParameterID int,
        summary int
);*/



public class TestResult {
    @Id
    private Integer testResultID;
    private Integer testParameterID;
    private Integer testUserID;
    private Integer summary;
}
