package ru.epozin.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.epozin.otus.library.dao.BookDao;
import ru.epozin.otus.library.domain.Book;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookDao.getById(id).orElse(null);
    }

    @Override
    public void deleteBook(Long id) {
        bookDao.deleteById(id);
    }

    @Override
    public void updateBook(Book book) {
        bookDao.insert(book);

    }
}
