package ru.nightmare.diplomny.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TestInstanceRedirection {
    @Id
    private int testInstanceRedirectionID;
    private int userID;
    private int testID;
    private int testAnswerID;
    private int redirectedToNumber;
}
