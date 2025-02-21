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
public class TestUser {
    @Id
    private Integer testUserID;
    private Integer userID;
    private Integer testID;
    private Boolean passed;
    @MappedCollection(idColumn = "test_user_id", keyColumn = "redirected_to_number")
    private transient Set<TestInstanceRedirection> redirection;
    @MappedCollection(idColumn = "test_user_id")
    private transient Set<TestUserAnswer> testUserAnswer;
    @MappedCollection(idColumn = "test_user_id")
    private transient Set<TestResult> testResult;

    public TestUser(Integer testUserID, Integer userID, Integer testID, Boolean passed) {
        this.testUserID = testUserID;
        this.userID = userID;
        this.testID = testID;
        this.passed = passed;
    }
}
