package ru.nightmare.diplomny.entity;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TestInstanceRedirection {
    private int testInstanceRedirectionId;
    private int userId;
    private int testId;
    private int testAnswerId;
    private int redirectedToNumber;
}
