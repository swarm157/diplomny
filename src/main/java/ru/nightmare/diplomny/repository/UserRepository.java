package ru.nightmare.diplomny.repository;

import org.springframework.data.repository.CrudRepository;
import ru.nightmare.diplomny.entity.Test;
import ru.nightmare.diplomny.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String email);
}
