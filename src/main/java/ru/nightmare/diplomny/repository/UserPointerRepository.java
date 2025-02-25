package ru.nightmare.diplomny.repository;

import org.springframework.data.repository.CrudRepository;
import ru.nightmare.diplomny.entity.UserPointer;
import ru.nightmare.diplomny.entity.UserState;

public interface UserPointerRepository extends CrudRepository<UserPointer, Integer> {
    UserPointer findByUserID(int userID);
}
