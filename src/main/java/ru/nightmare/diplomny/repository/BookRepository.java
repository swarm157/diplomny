package ru.nightmare.diplomny.repository;

import org.springframework.data.repository.CrudRepository;
import ru.nightmare.diplomny.entity.Book;
import ru.nightmare.diplomny.entity.TestAnswer;

public interface BookRepository extends CrudRepository<Book, Integer> {
    Iterable<Book> findByCategoryID(int categoryID);
}
