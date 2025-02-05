package ru.nightmare.diplomny.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class MyController {

    private static final String niy = "not implemented yet";

    @PostMapping("/user")
    public String getUser(int id) {
        return niy;
    }

    @GetMapping
    public String getTest() {
        return niy;
    }

    @GetMapping
    public String getTestAnswer() {
        return niy;
    }

}
