package ru.nightmare.diplomny.repository;

import org.springframework.data.repository.CrudRepository;
import ru.nightmare.diplomny.entity.Test;
import ru.nightmare.diplomny.entity.TestParameter;

public interface TestParameterRepository extends CrudRepository<TestParameter, Integer> {
}
