package ru.nightmare.diplomny.model;


import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nightmare.diplomny.entity.Test;
import ru.nightmare.diplomny.entity.TestQuestion;
import ru.nightmare.diplomny.entity.User;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Component
@NoArgsConstructor
public class DBController {

    @Autowired
    DataSource source;

    private Statement getStatement() throws SQLException {
        return source.getConnection().createStatement();
    }
    public List<String> getAnswers(TestQuestion question, User user) {
        return null;
    }

    public TestProcessor testFor(User user, Test test) {
        return null;
    }
}
