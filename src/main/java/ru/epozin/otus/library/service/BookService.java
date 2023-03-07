package ru.epozin.otus.library.service;


import ru.epozin.otus.library.domain.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();

    Book getBookById(Long id);

    void deleteBook(Long id);

    void updateBook(Book book);

}
