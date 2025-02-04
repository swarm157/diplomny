package ru.nightmare.diplomny.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Test {
    @Id
    private int testID;
    private String name;
    private String description;
    private Integer previousId;
}
