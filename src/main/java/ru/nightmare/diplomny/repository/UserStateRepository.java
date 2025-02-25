package ru.nightmare.diplomny.repository;

import org.springframework.data.repository.CrudRepository;
import ru.nightmare.diplomny.entity.UserPointer;
import ru.nightmare.diplomny.entity.UserState;

public interface UserStateRepository extends CrudRepository<UserState, Integer> {
}
