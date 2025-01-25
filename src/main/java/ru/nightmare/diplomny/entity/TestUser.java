package ru.nightmare.diplomny.entity;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TestUser {
    private int testUserId;
    private int userId;
    private int testId;
    private String answers;
    private boolean passed;
}
