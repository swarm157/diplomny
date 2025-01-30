package ru.nightmare.diplomny.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TestUser {
    @Id
    private int testUserId;
    private int userId;
    private int testId;
    private String answers;
    private boolean passed;
}
