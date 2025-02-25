package ru.nightmare.diplomny.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserPointer {
    @Id
    private Integer userPointerID;
    private Integer userStateID;
    private Integer userID;
    private Integer pointer;
}
/*
CREATE TABLE user_pointer (
    user_pointer_id int NOT NULL,
    user_state_id int NOT NULL,
    user_id int NOT NULL,
    pointer int NOT NULL,
    CONSTRAINT user_pointer_pk PRIMARY KEY (user_pointer_id),
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES _user(user_id) ON DELETE CASCADE,
    CONSTRAINT user_state_id_fk FOREIGN KEY (user_state_id) REFERENCES user_state(user_state_id) ON DELETE CASCADE
);
 */