package ru.epozin.otus.library.dao;

import ru.epozin.otus.library.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    void insert(Author author);

    Optional<Author> getById(long id);

    List<Author> getAll();

    void deleteById(long id);
}
