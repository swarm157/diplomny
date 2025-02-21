package ru.nightmare.diplomny.repository;

import org.springframework.data.repository.CrudRepository;
import ru.nightmare.diplomny.entity.Test;
import ru.nightmare.diplomny.entity.TestAnswer;

public interface TestAnswerRepository extends CrudRepository<TestAnswer, Integer> {
}
