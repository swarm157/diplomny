package ru.nightmare.diplomny.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.sql.Date;
import java.util.Arrays;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class User {
    @Id
    private int userID;
    private String name;
    private String lastName;
    private Date registration;
    private boolean admin;
    private String email;
    private String password;
    private String description;
    private byte[] avatar;
}
