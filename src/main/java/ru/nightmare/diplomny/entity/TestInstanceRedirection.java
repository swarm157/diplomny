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
    private int testInstanceRedirectionId;
    private int userId;
    private int testId;
    private int testAnswerId;
    private int redirectedToNumber;
}
