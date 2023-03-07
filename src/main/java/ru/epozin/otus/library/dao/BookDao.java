package ru.epozin.otus.library.dao;

import ru.epozin.otus.library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    void insert(Book book);

    Optional<Book> getById(long id);

    List<Book> getAll();

    void deleteById(long id);
}
