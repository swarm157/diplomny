package ru.nightmare.diplomny.repository;

import org.springframework.data.repository.CrudRepository;
import ru.nightmare.diplomny.entity.Test;
import ru.nightmare.diplomny.entity.TestInstanceRedirection;

public interface TestInstanceRedirectionRepository extends CrudRepository<TestInstanceRedirection, Integer> {
}
