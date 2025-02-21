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
public class Test {
    @Id
    private Integer testID;
    private String name;
    private String description;
    private Integer previousId;
    @MappedCollection(idColumn = "previous_id")
    private transient Set<Test> test;
    @MappedCollection(idColumn = "test_id", keyColumn = "number_in_order")
    private transient Set<TestQuestion> testQuestion;
    @MappedCollection(idColumn = "test_id")
    private transient Set<TestUser> testUser;
    @MappedCollection(idColumn = "test_id")
    private transient Set<TestParameter> testParameter;

    public Test(Integer testID, String name, String description, Integer previousId) {
        this.testID = testID;
        this.name = name;
        this.description = description;
        this.previousId = previousId;
    }
}
