package ru.nightmare.diplomny.repository;

import org.springframework.data.repository.CrudRepository;
import ru.nightmare.diplomny.entity.Test;
import ru.nightmare.diplomny.entity.TestUser;

public interface TestUserRepository extends CrudRepository<TestUser, Integer> {
}
