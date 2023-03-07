package ru.epozin.otus.library.dao;

import ru.epozin.otus.library.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    void insert(Genre genre);

    Optional<Genre> getById(long id);

    List<Genre> getAll();

    void deleteById(long id);
}
