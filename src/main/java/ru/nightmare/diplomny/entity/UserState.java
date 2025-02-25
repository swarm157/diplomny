package ru.nightmare.diplomny.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserState {
    @Id
    private Integer userStateID;
    private String state;

    @MappedCollection(idColumn = "user_state_id")
    private transient Set<UserPointer> pointers;
}
/*
CREATE table user_state (
    user_state_id int NOT NULL,
    state varchar(8) NOT NULL,
    CONSTRAINT user_state_pk PRIMARY KEY (user_state_id)
);
 */
