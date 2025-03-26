package ru.nightmare.diplomny.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nightmare.diplomny.entity.Book;
import ru.nightmare.diplomny.entity.TestAnswerReward;
import ru.nightmare.diplomny.repository.BookRepository;
import ru.nightmare.diplomny.repository.TestAnswerRewardRepository;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    // Create
    public Book createBook(String name, String file, Integer categoryId, Byte[] preview) {
        Book book = new Book();
        book.setFile(file);
        book.setName(name);
        book.setPreview(preview);
        book.setCategoryID(categoryId);
        return bookRepository.save(book);
    }

    // Read
    public Iterable<Book> getAllBooks(int categoryId) {
        return bookRepository.findByCategoryID(categoryId);
    }

    public Book getBook(int id) {
        return bookRepository.findById(id).orElseThrow();
    }

    // Update
    public Book updateBook(int id, String name, String file, Integer categoryId, Byte[] preview) {
        Book book = bookRepository.findById(id).orElseThrow();
        book.setFile(file);
        book.setName(name);
        book.setPreview(preview);
        book.setCategoryID(categoryId);
        return bookRepository.save(book);
    }

    // Delete
    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }
}