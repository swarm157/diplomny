package ru.nightmare.diplomny.entity;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Test {
    private int testId;
    private String name;
    private String description;
    private Integer previousId;
}
