package ru.epozin.otus.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.epozin.otus.library.domain.Author;
import ru.epozin.otus.library.domain.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class BookDaoImplTest {
    private static final Long EXISTING_BOOK_ID = 1L;
    private static final String EXISTING_BOOK_TITLE = "Dune";

    @Autowired
    private BookDao bookDao;
    @Autowired
    private GenreDao genreDao;
    @Autowired
    private AuthorDao authorDao;

    @DisplayName("Добавление книги в БД")
    @Test
    void shouldInsertBook() {
        Book expectedBook = new Book(2L, "Dune",genreDao.getById(1L).orElse(null),authorDao.getById(1L).orElse(null));
        bookDao.insert(expectedBook);
        Book actualBook = bookDao.getById(expectedBook.getId()).orElse(null);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Получение ожидаемой книги по ее id")
    @Test
    void shouldReturnExpectedBookById() {
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_TITLE, genreDao.getById(1L).orElse(null),authorDao.getById(1L).orElse(null));
        Book actualBook = bookDao.getById(expectedBook.getId()).orElse(null);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Удаление заданной книги по ее id")
    @Test
    void shouldCorrectDeleteBookById() {
        assertThatCode(() -> bookDao.getById(EXISTING_BOOK_ID))
                .doesNotThrowAnyException();

        bookDao.deleteById(EXISTING_BOOK_ID);

        assertThatThrownBy(() -> bookDao.getById(EXISTING_BOOK_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("Получение ожидаемого списка книг")
    @Test
    void shouldReturnExpectedBooksList() {
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_TITLE, genreDao.getById(1L).orElse(null),authorDao.getById(1L).orElse(null));
        List<Book> actualBookList = bookDao.getAll();
        assertThat(actualBookList)
                .containsExactlyInAnyOrder(expectedBook);
    }

}
