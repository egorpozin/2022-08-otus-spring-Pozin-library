package ru.epozin.otus.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import ru.epozin.otus.library.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Import(AuthorDaoImpl.class)
public class AuthorDaoImplTest {
    
        private static final Long EXISTING_AUTHOR_ID = 1L;
        private static final String EXISTING_AUTHOR_NAME = "Frank Herbert";

        @Autowired
        private AuthorDao authorDao;

        @DisplayName("Добавление автора в БД")
        @Test
        void shouldInsertAuthor() {
            Author expectedAuthor = new Author(2L, "Frank Herbert");
            authorDao.insert(expectedAuthor);
            Author actualAuthor = authorDao.getById(expectedAuthor.getId()).orElse(null);
            assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
        }

        @DisplayName("Получение ожидаемого автора по его id")
        @Test
        void shouldReturnExpectedAuthorById() {
            Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
            Author actualAuthor = authorDao.getById(expectedAuthor.getId()).orElse(null);
            assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
        }

        @DisplayName("Удаление заданного автора по его id")
        @Test
        void shouldCorrectDeleteAuthorById() {
            assertThatCode(() -> authorDao.getById(EXISTING_AUTHOR_ID))
                    .doesNotThrowAnyException();

            authorDao.deleteById(EXISTING_AUTHOR_ID);

            assertThatThrownBy(() -> authorDao.getById(EXISTING_AUTHOR_ID))
                    .isInstanceOf(EmptyResultDataAccessException.class);
        }

        @DisplayName("Получение ожидаемого списка авторов")
        @Test
        void shouldReturnExpectedAuthorsList() {
            Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
            List<Author> actualAuthorList = authorDao.getAll();
            assertThat(actualAuthorList)
                    .containsExactlyInAnyOrder(expectedAuthor);
        }

}