package ru.nightmare.diplomny.entity;

import lombok.*;

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
    private int userId;
    private String name;
    private String lastName;
    private Date registration;
    private boolean admin;
    private String email;
    private String password;
    private String description;
    private byte[] avatar;
}
