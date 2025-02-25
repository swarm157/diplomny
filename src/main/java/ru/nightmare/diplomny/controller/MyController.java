package ru.nightmare.diplomny.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nightmare.diplomny.entity.User;
import ru.nightmare.diplomny.entity.UserPointer;
import ru.nightmare.diplomny.model.TestController;


@RestController
@RequestMapping("/api")
/*
    Итак в любом случае сейчас бутстрап студио лежит и вставать не планирует
    Значит чё, определиться чё я хочу
    Хорошая идея потому-что я хз чё именно буду делать с этим классом
    Наверное первым делом чтоб не наломать дров нужно было бы разобраться с тем как тут
    работают сессии и регистрация
    Ну, с первым будет достаточно
    Поскольку я делаю всё на куяксе, кроме как пустые бланки страниц пользователь ничего не сможет получить
    Ибо всё идет через json-ки.
    Когда TestController будет дописан станет конечно гораздо легче, но я чую лучше будет прыгнуть сюда сначала
    Итак, мне нужно как-то получать, то от какого именно пользователя пришёл тот или иной запрос
    Я пока-что размечу пользователя.
    Обожаю за это жабу, ты ещё не знаешь, как именно это делать, но уже можешь это делать, спасибо абстракциям
    Гораздо проче заниматься простыми задачами ставя их в поток нежели знать всё и сразу, ну и так просто удобнее
 */
public class MyController {

    @Autowired
    private TestController api;
    private static final String niy = "not implemented yet";

    private User user;
    private boolean logged;

    @GetMapping("/user")
    public String getUser(@PathVariable Integer id) {
        return api.getUserById(id);
    }

    @GetMapping("/test")
    public String getTest() {
        return niy;
    }

    @PostMapping("/answer")
    public String testAnswer() {
        return niy;
    }

    @GetMapping("/question")
    public String nextQuestion() {
        return niy;
    }

    @PostMapping("/register")
    public String register() {
        return niy;
    }

    @PostMapping("/login")
    public String login() {
        return niy;
    }
    @GetMapping("/logout")
    public String logout() {
        return niy;
    }
    @GetMapping("/avatar")
    public String avatar(){
        return niy;
    }
    @PostMapping("/changeAvatar")
    public String changeAvatar() {
        return niy;
    }
    @PostMapping("/changeDescription")
    public String changeDescription() {
        return niy;
    }
    @GetMapping("/beginTesting")
    public String beginTesting() {
        return niy;
    }
    @GetMapping("/goToTheResults")
    public String goToTheResults() {
        return niy;
    }
    @GetMapping("/endUpTesting")
    public String endUpTesting() {
        return niy;
    }
    @GetMapping("/getTests")
    public String getTests() {
        return niy;
    }
}
