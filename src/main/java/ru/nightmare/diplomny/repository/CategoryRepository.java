package ru.nightmare.diplomny.repository;

import org.springframework.data.repository.CrudRepository;
import ru.nightmare.diplomny.entity.Category;
import ru.nightmare.diplomny.entity.TestAnswer;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
}
