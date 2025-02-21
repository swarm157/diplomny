package ru.nightmare.diplomny.repository;

import org.springframework.data.repository.CrudRepository;
import ru.nightmare.diplomny.entity.TestAnswer;

public interface TestAnswerRepository extends CrudRepository<TestAnswer, Integer> {
    Iterable<TestAnswer> findTestAnswerByTestQuestionID(int testQuestionID);
}
