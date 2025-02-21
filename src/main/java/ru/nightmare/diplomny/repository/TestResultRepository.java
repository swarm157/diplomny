package ru.nightmare.diplomny.repository;

import org.springframework.data.repository.CrudRepository;
import ru.nightmare.diplomny.entity.Test;
import ru.nightmare.diplomny.entity.TestResult;

public interface TestResultRepository extends CrudRepository<TestResult, Integer> {
    Iterable<TestResult> findAllByTestUserID(int testUserID);
}
