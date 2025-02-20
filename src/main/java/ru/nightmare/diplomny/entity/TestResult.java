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
    private int testResultID;
    private int testParameterID;
    private int testUserID;
    private int summary;
}
