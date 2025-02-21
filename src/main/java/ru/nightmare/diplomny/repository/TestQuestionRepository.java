package ru.nightmare.diplomny.repository;

import org.springframework.data.repository.CrudRepository;
import ru.nightmare.diplomny.entity.Test;
import ru.nightmare.diplomny.entity.TestQuestion;

public interface TestQuestionRepository extends CrudRepository<TestQuestion, Integer> {
    Iterable<TestQuestion> findAllByTestID(int testID);

    Iterable<TestQuestion> findAllByTestIDOrderByNumberInOrderAsc(int testID);

    TestQuestion findByTestIDAndNumberInOrder(int testID, int numberInOrder);
}
