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
    private int testUserID;
    private int userID;
    private int testID;
    private boolean passed;
}
