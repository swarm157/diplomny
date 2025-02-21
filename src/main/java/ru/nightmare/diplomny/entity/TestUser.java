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
    private Integer testUserID;
    private Integer userID;
    private Integer testID;
    private Boolean passed;
}
