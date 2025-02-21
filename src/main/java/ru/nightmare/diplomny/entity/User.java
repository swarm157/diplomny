package ru.nightmare.diplomny.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table("_user")
public class User {
    @Id
    private Integer userID;
    private String name;
    private String lastName;
    private Date registration;
    private Boolean admin;
    private String email;
    private String password;
    private String description;
    private Byte[] avatar;

    @MappedCollection(idColumn = "user_id")
    private Set<TestUser> testUsers;

}
