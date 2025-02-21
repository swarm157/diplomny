package ru.nightmare.diplomny.repository;

import org.springframework.data.repository.CrudRepository;
import ru.nightmare.diplomny.entity.Test;
import ru.nightmare.diplomny.entity.TestUserAnswer;

public interface TestUserAnswerRepository extends CrudRepository<TestUserAnswer, Integer> {
}
