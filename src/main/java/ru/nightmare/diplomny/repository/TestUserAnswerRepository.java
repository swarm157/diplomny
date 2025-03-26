package ru.nightmare.diplomny.repository;

import org.springframework.data.repository.CrudRepository;
import ru.nightmare.diplomny.entity.Test;
import ru.nightmare.diplomny.entity.TestUserAnswer;

public interface TestUserAnswerRepository extends CrudRepository<TestUserAnswer, Integer> {
    Iterable<TestUserAnswer> findByTestUserID(int testUserID);

    TestUserAnswer findByTestUserIDOrderByAnsweredAsc(int testUserID);

    TestUserAnswer findByTestUserIDOrderByAnsweredDesc(int testUserID);

    TestUserAnswer findByTestQuestionIDAndTestUserID(Integer testQuestionID, Integer testUserID);
}
