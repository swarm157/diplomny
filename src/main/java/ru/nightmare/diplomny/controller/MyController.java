package ru.nightmare.diplomny.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nightmare.diplomny.entity.*;
import ru.nightmare.diplomny.model.TestController;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;


@Slf4j
@RestController
@RequestMapping("/api")
/*
     Не забыть решить, как собственно делать библиотеку. SQL сделал на случай чего
     Не забыть про профиль
     Готово Не забыть обернуть всё в ResponseEntity

     Допишу, теперь у такого разделения есть смысл, упрощение работы, работа с данными разделена
     на работу с сугубо базой и на работу с сессиями, а также на обработку самих данных.
     Ну и загрузку, но этот четвертый слой абстракций находится уже на фронтенде, а туда я пока не дошел
     кроме, как написания базового макета, ещё даже толком не красил.

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

    @Autowired
    private Gson gson;
    private static final String niy = "not implemented yet";

    @PostMapping("/user")
    public ResponseEntity<String> getUser(@RequestParam Integer id) {
        String t = api.getUserById(id);
        if(t==null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(t);
    }

    @PostMapping("/test")
    public ResponseEntity<String> getTest(@RequestParam Integer id) {
        if(id!=null)
            return ResponseEntity.ok(api.getTest(id));
        return ResponseEntity.notFound().build();
    }


    @PostMapping("/question")
    public ResponseEntity<String> nextQuestion(HttpSession session, @RequestParam Integer answer) throws SQLException {
        User user = (User) session.getAttribute("user");
        if(user!=null) {
            if(api.getPointer(user).getUserStateID()!=2)
                return ResponseEntity.badRequest().build();
            api.takeUserAnswer(user, Objects.requireNonNullElse(answer, -2));
            String question = api.getNextQuestion(api.getTestUser((UserPointer)session.getAttribute("pointer")));
            session.setAttribute("pointer", api.getPointer(user));
            return ResponseEntity.ok(question);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(HttpSession session, @RequestParam String email,
                         @RequestParam String password, @RequestParam String name,
                         @RequestParam String lastName, @RequestParam String description) {
        User user = null;
        try {
            user = gson.fromJson(api.registerUser(name, lastName, email, description, password), User.class);
            session.setAttribute("user", user);
            if(user!=null) {
                UserPointer pointer = api.getPointer(user);
                if (pointer != null) {
                    session.setAttribute("pointer", pointer);
                }
                session.setAttribute("user", user);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(HttpSession session,
    @RequestParam String email, @RequestParam String password) {
        User user = null;
        try {
            user = gson.fromJson(api.loginUser(email, password), User.class);
            if(user!=null) {
                UserPointer pointer = api.getPointer(user);
                if (pointer != null) {
                    session.setAttribute("pointer", pointer);
                }
                session.setAttribute("user", user);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(user);
    }
    @PostMapping("/logout")
    public ResponseEntity<User> logout(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user!=null) {
            session.removeAttribute("user");
            session.removeAttribute("pointer");
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().build();
    }
    @GetMapping("/avatar")
    public ResponseEntity<Byte[]> avatar(HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user!=null) {
            return ResponseEntity.ok(user.getAvatar());
        }
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/changeAvatar")
    public ResponseEntity<Byte[]> changeAvatar(HttpSession session, @RequestParam Byte[] avatar) {
        if(avatar!=null)
        {
            User user = (User) session.getAttribute("user");
            if(user!=null) {
                if(((UserPointer)session.getAttribute("pointer")).getUserStateID()!=1)
                    return ResponseEntity.badRequest().build();
                user.setAvatar(avatar);
                user = api.changeUser(user);
                session.setAttribute("user", user);
                return ResponseEntity.ok(user.getAvatar());
            }
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/changeDescription")
    public ResponseEntity<String> changeDescription(HttpSession session, @RequestParam String description) throws SQLException {
        User user = (User) session.getAttribute("user");
        if(user!=null) {
            if(((UserPointer)session.getAttribute("pointer")).getUserStateID()!=1)
                return ResponseEntity.badRequest().build();
            user.setDescription(description);
            user = api.changeUser(user);
            session.setAttribute("user", user);
            return ResponseEntity.ok(user.getDescription());
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/beginTesting")
    public ResponseEntity<String> beginTesting(HttpSession session, @RequestParam Integer id) throws SQLException {
        User user = (User) session.getAttribute("user");
        if(user!=null&&id!=null) {
            String t = api.beginTesting(user, id);
            session.setAttribute("pointer", api.getPointer(user));
            return ResponseEntity.ok(t);
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/result")
    public ResponseEntity<String> getResult(HttpSession session, @RequestParam Integer id) throws SQLException {
        User user = (User) session.getAttribute("user");
        if(user!=null) {
            String t = api.getResult(api.getTestUser(user, id));
            session.setAttribute("pointer", api.getPointer(user));
            return ResponseEntity.ok(t);
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/endUpTesting")
    public ResponseEntity<String> endUpTesting(HttpSession session) throws SQLException {
        User user = (User) session.getAttribute("user");
        if(user!=null) {
            String t = api.endUpTesting(user);
            session.setAttribute("pointer", api.getPointer(user));
            return ResponseEntity.ok(t);
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/getTests")
    public ResponseEntity<String> getTests(HttpSession session) throws SQLException {
        return ResponseEntity.ok(api.getAllTests((User)session.getAttribute("user")));
    }

    @PostMapping("/getTimeUsed")
    public ResponseEntity<Integer> getTimeUsed(HttpSession session, @RequestParam Integer id) {
        if(session.getAttribute("user")!=null) {
            User user = (User) session.getAttribute("user");
            return ResponseEntity.ok(api.getTimeUsed(user, id));
        }
        return ResponseEntity.badRequest().build();
    }




    /*
        Время писать для админки
     */
    @PostMapping("/createTest")
    public ResponseEntity<String> createTest(HttpSession session, @RequestParam String name, @RequestParam String description) throws SQLException {
        if(isAdmin(session)) {
            User user = (User) session.getAttribute("user");
            UserPointer pointer = (UserPointer) session.getAttribute("pointer");
            if(pointer!=null&&pointer.getUserStateID()==1) {
                if(name!=null && description!=null) {
                    return ResponseEntity.ok(api.createTest(name, description));
                }
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/changeTest")
    public ResponseEntity<String> changeTest(HttpSession session, @RequestParam Integer id, @RequestParam String name, @RequestParam String description, @RequestParam Integer prev) throws SQLException {
        if(isAdmin(session)) {
            User user = (User) session.getAttribute("user");
            UserPointer pointer = (UserPointer) session.getAttribute("pointer");
            if(pointer!=null&&pointer.getUserStateID()==1) {
                if(name!=null && description!=null && id!=null) {
                    if(prev!=null) {
                        return ResponseEntity.ok(api.changeTest(id, name, description, prev));
                    } else {
                        return ResponseEntity.ok(api.changeTest(id, name, description));
                    }
                }
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/deleteTest")
    public ResponseEntity<String> deleteTest(HttpSession session, @RequestParam Integer id) throws SQLException {
        if(isAdmin(session)) {
            User user = (User) session.getAttribute("user");
            UserPointer pointer = (UserPointer) session.getAttribute("pointer");
            if(pointer!=null&&pointer.getUserStateID()==1) {
                if(id!=null)
                    return ResponseEntity.ok(api.deleteTest(id));
            }
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/getAllTests")
    public ResponseEntity<List<Test>> getAllTests(HttpSession session) throws SQLException {
        if(isAdmin(session)) {
            User user = (User) session.getAttribute("user");
            UserPointer pointer = (UserPointer) session.getAttribute("pointer");
            if(pointer!=null&&pointer.getUserStateID()==1) {
                return ResponseEntity.ok(api.getAllTests());
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/createAnswer")
    public ResponseEntity<TestAnswer> createAnswer(HttpSession session, @RequestParam Integer qid, @RequestParam String name) {
        if(isAdmin(session)) {
            User user = (User) session.getAttribute("user");
            UserPointer pointer = (UserPointer) session.getAttribute("pointer");
            if(pointer!=null&&pointer.getUserStateID()==1) {
                if(qid!=null && name!=null)
                    return ResponseEntity.ok(api.createAnswer(qid, name));
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/changeAnswer")
    public ResponseEntity<TestAnswer> changeAnswer(HttpSession session, @RequestParam Integer qid, @RequestParam String name, @RequestParam Integer id) {
        if(isAdmin(session)) {
            User user = (User) session.getAttribute("user");
            UserPointer pointer = (UserPointer) session.getAttribute("pointer");
            if(pointer!=null&&pointer.getUserStateID()==1) {
                if(qid!=null && id!=null && name!=null)
                    return ResponseEntity.ok(api.changeAnswer(id, qid, name));
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/deleteAnswer")
    public ResponseEntity<String> deleteAnswer(HttpSession session, @RequestParam Integer id) throws SQLException {
        if(isAdmin(session)) {
            User user = (User) session.getAttribute("user");
            UserPointer pointer = (UserPointer) session.getAttribute("pointer");
            if(pointer!=null&&pointer.getUserStateID()==1) {
                if(id!=null)
                    ResponseEntity.ok(api.deleteAnswer(id));
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/getAllAnswers")
    public ResponseEntity<List<TestAnswer>> getAllAnswers(HttpSession session, @RequestParam Integer qid) {
        if(isAdmin(session)) {
            User user = (User) session.getAttribute("user");
            UserPointer pointer = (UserPointer) session.getAttribute("pointer");
            if(pointer!=null&&pointer.getUserStateID()==1) {
                if(qid!=null)
                    return ResponseEntity.ok(api.getAllAnswers(qid));
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/createQuestion")
    public ResponseEntity<String> createQuestion(HttpSession session, @RequestParam Integer id) throws SQLException {
        if(isAdmin(session)) {
            User user = (User) session.getAttribute("user");
            UserPointer pointer = (UserPointer) session.getAttribute("pointer");
            if(pointer!=null&&pointer.getUserStateID()==1) {
                if(id!=null)
                    return ResponseEntity.ok(api.createQuestion(id));
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/changeQuestion")
    public ResponseEntity<String> changeQuestion(HttpSession session, @RequestParam Integer id,  @RequestParam String name,  @RequestParam Integer testId,  @RequestParam Integer inOrder,  @RequestParam Integer perInstance) throws SQLException {
        if(isAdmin(session)) {
            User user = (User) session.getAttribute("user");
            UserPointer pointer = (UserPointer) session.getAttribute("pointer");
            if(pointer!=null&&pointer.getUserStateID()==1) {
                if(id!=null && name!=null && testId!=null && inOrder !=null && perInstance!=null)
                    return ResponseEntity.ok(api.changeQuestion(id, name, testId, inOrder, perInstance));
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/deleteQuestion")
    public ResponseEntity<String> deleteQuestion(HttpSession session, @RequestParam Integer id) throws SQLException {
        if(isAdmin(session)) {
            User user = (User) session.getAttribute("user");
            UserPointer pointer = (UserPointer) session.getAttribute("pointer");
            if(pointer!=null&&pointer.getUserStateID()==1) {
                if(id!=null)
                    return ResponseEntity.ok(api.deleteQuestion(id));
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/getAllQuestions")
    public ResponseEntity<String> getAllQuestions(HttpSession session, @RequestParam Integer id) throws SQLException {
        if(isAdmin(session)) {
            User user = (User) session.getAttribute("user");
            UserPointer pointer = (UserPointer) session.getAttribute("pointer");
            if(pointer!=null&&pointer.getUserStateID()==1) {
                if(id!=null)
                    return ResponseEntity.ok(api.getAllQuestions(id));
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/createReward")
    public ResponseEntity<String> createReward(HttpSession session, @RequestParam Integer id, @RequestParam Integer reward, @RequestParam Integer parameterId) throws SQLException {
        if(isAdmin(session)) {
            User user = (User) session.getAttribute("user");
            UserPointer pointer = (UserPointer) session.getAttribute("pointer");
            if(pointer!=null&&pointer.getUserStateID()==1) {
                if(id!=null && reward!=null && parameterId!=null)
                    return ResponseEntity.ok(api.createReward(id, reward, parameterId));
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/changeReward")
    public ResponseEntity<String> changeReward(HttpSession session, @RequestParam Integer id, @RequestParam Integer reward, @RequestParam Integer parameterId, @RequestParam Integer answerId) throws SQLException {
        if(isAdmin(session)) {
            User user = (User) session.getAttribute("user");
            UserPointer pointer = (UserPointer) session.getAttribute("pointer");
            if(pointer!=null&&pointer.getUserStateID()==1) {
                if(id!=null)
                    return ResponseEntity.ok(api.changeReward(id, reward, parameterId, answerId));
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/deleteReward")
    public ResponseEntity<String> deleteReward(HttpSession session, @RequestParam Integer id) throws SQLException {
        if(isAdmin(session)) {
            User user = (User) session.getAttribute("user");
            UserPointer pointer = (UserPointer) session.getAttribute("pointer");
            if(pointer!=null&&pointer.getUserStateID()==1) {
                if(id!=null)
                    return ResponseEntity.ok(api.deleteReward(id));
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/getAllRewards")
    public ResponseEntity<String> getAllRewards(HttpSession session, @RequestParam Integer id) throws SQLException {
        if(isAdmin(session)) {
            User user = (User) session.getAttribute("user");
            UserPointer pointer = (UserPointer) session.getAttribute("pointer");
            if(pointer!=null&&pointer.getUserStateID()==1) {
                if(id!=null)
                    return ResponseEntity.ok(api.getAllRewards(id));
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/createParameter")
    public ResponseEntity<String> createParameter(HttpSession session, @RequestParam String name, @RequestParam Integer required, @RequestParam Integer prevRequired, @RequestParam Integer testId) throws SQLException {
        if(isAdmin(session)) {
            User user = (User) session.getAttribute("user");
            UserPointer pointer = (UserPointer) session.getAttribute("pointer");
            if(pointer!=null&&pointer.getUserStateID()==1) {
                if(name!=null && required!=null && prevRequired!=null && testId!=null)
                    return ResponseEntity.ok(api.createParameter(name, required, prevRequired, testId));
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/changeParameter")
    public ResponseEntity<String> changeParameter(HttpSession session, @RequestParam Integer id, @RequestParam String name, @RequestParam Integer required, @RequestParam Integer prevRequired, @RequestParam Integer testId) throws SQLException {
        if(isAdmin(session)) {
            User user = (User) session.getAttribute("user");
            UserPointer pointer = (UserPointer) session.getAttribute("pointer");
            if(pointer!=null&&pointer.getUserStateID()==1) {
                if(id!=null && name!=null && required!=null && prevRequired!=null && testId!=null)
                    return ResponseEntity.ok(api.changeParameter(id, name, required, prevRequired, testId));
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/deleteParameter")
    public ResponseEntity<String> deleteParameter(HttpSession session, @RequestParam Integer id) throws SQLException {
        if(isAdmin(session)) {
            User user = (User) session.getAttribute("user");
            UserPointer pointer = (UserPointer) session.getAttribute("pointer");
            if(pointer!=null&&pointer.getUserStateID()==1) {
                if(id!=null)
                    return ResponseEntity.ok(api.deleteParameter(id));
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/getAllParameters")
    public ResponseEntity<String> getAllParameters(HttpSession session, @RequestParam Integer id) throws SQLException {
        if(isAdmin(session)) {
            User user = (User) session.getAttribute("user");
            UserPointer pointer = (UserPointer) session.getAttribute("pointer");
            if(pointer!=null&&pointer.getUserStateID()==1) {
                if(id!=null)
                    return ResponseEntity.ok(api.getAllParameters(id));
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }





    @PostMapping("/createCategory")
    public ResponseEntity<String> createCategory(HttpSession session, @RequestParam String name) throws SQLException {
        if(isAdmin(session)) {
            User user = (User) session.getAttribute("user");
            UserPointer pointer = (UserPointer) session.getAttribute("pointer");
            if(pointer!=null&&pointer.getUserStateID()==1) {
                if(name!=null)
                    return ResponseEntity.ok(api.createCategory(name));
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/changeCategory")
    public ResponseEntity<String> changeCategory(HttpSession session, @RequestParam Integer id, @RequestParam String name) throws SQLException {
        if(isAdmin(session)) {
            User user = (User) session.getAttribute("user");
            UserPointer pointer = (UserPointer) session.getAttribute("pointer");
            if(pointer!=null&&pointer.getUserStateID()==1) {
                if(id!=null && name!=null)
                    return ResponseEntity.ok(api.changeCategory(id, name));
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/deleteCategory")
    public ResponseEntity<String> deleteCategory(HttpSession session, @RequestParam Integer id) throws SQLException {
        if(isAdmin(session)) {
            User user = (User) session.getAttribute("user");
            UserPointer pointer = (UserPointer) session.getAttribute("pointer");
            if(pointer!=null&&pointer.getUserStateID()==1) {
                if(id!=null)
                    return ResponseEntity.ok(api.deleteCategory(id));
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/getAllCategories")
    public ResponseEntity<String> getAllCategories(HttpSession session) throws SQLException {
            User user = (User) session.getAttribute("user");
            if(user!=null) {
                UserPointer pointer = (UserPointer) session.getAttribute("pointer");
                if(pointer!=null&&pointer.getUserStateID()==1) {
                    return ResponseEntity.ok(api.getAllCategory());
                } else {
                    return ResponseEntity.badRequest().build();
                }
            }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/createBook")
    public ResponseEntity<String> createBook(HttpSession session, @RequestParam String name, @RequestParam String file, @RequestParam Integer categoryId, @RequestParam Byte[] preview) throws SQLException {
        if(isAdmin(session)) {
            User user = (User) session.getAttribute("user");
            UserPointer pointer = (UserPointer) session.getAttribute("pointer");
            if(pointer!=null&&pointer.getUserStateID()==1) {
                if(name!=null&&file!=null&&categoryId!=null&&preview!=null)
                    return ResponseEntity.ok(api.createBook(name, file, categoryId, preview));
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/changeBook")
    public ResponseEntity<String> changeBook(HttpSession session, @RequestParam Integer id, @RequestParam String name, @RequestParam String file, @RequestParam Integer categoryId, @RequestParam Byte[] preview) throws SQLException {
        if(isAdmin(session)) {
            User user = (User) session.getAttribute("user");
            UserPointer pointer = (UserPointer) session.getAttribute("pointer");
            if(pointer!=null&&pointer.getUserStateID()==1) {
                if(id!=null&&name!=null&&file!=null&&categoryId!=null&&preview!=null)
                    return ResponseEntity.ok(api.changeBook(id, name, file, categoryId, preview));
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/deleteBook")
    public ResponseEntity<String> deleteBook(HttpSession session, @RequestParam Integer id) throws SQLException {
        if(isAdmin(session)) {
            User user = (User) session.getAttribute("user");
            UserPointer pointer = (UserPointer) session.getAttribute("pointer");
            if(pointer!=null&&pointer.getUserStateID()==1) {
                if(id!=null)
                    return ResponseEntity.ok(api.deleteBook(id));
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("/getAllBooks")
    public ResponseEntity<String> getAllBooks(HttpSession session, @RequestParam Integer id) throws SQLException {
            User user = (User) session.getAttribute("user");
            if(user!=null) {
                UserPointer pointer = (UserPointer) session.getAttribute("pointer");
                if (pointer != null && pointer.getUserStateID() == 1) {
                    if(id!=null)
                        return ResponseEntity.ok(api.getAllBooks(id));
                } else {
                    return ResponseEntity.badRequest().build();
                }
            }
        return ResponseEntity.badRequest().build();
    }

    private boolean isAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user==null)
            return false;
        return user.getAdmin();
    }
}