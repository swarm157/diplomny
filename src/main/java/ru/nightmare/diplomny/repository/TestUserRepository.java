package ru.nightmare.diplomny.repository;

import org.springframework.data.repository.CrudRepository;
import ru.nightmare.diplomny.entity.Test;
import ru.nightmare.diplomny.entity.TestAnswer;
import ru.nightmare.diplomny.entity.TestUser;


public interface TestUserRepository extends CrudRepository<TestUser, Integer> {
    TestUser findByUserIDAndTestID(int userID, int testID);

    Iterable<TestUser> findAllByTestUserID(int testUserID);
}
