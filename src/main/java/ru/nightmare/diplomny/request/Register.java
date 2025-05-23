package ru.nightmare.diplomny.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Register {
    String email;
    String password;
    String name;
    String lastName;
    String description;
}
