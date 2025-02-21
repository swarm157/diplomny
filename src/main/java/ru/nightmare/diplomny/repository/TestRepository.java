package ru.nightmare.diplomny.repository;

import org.springframework.data.repository.CrudRepository;
import ru.nightmare.diplomny.entity.Test;

public interface TestRepository extends CrudRepository<Test, Integer> {
}
