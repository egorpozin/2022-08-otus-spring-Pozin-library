package ru.epozin.otus.library.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;
import ru.epozin.otus.library.domain.Author;
import ru.epozin.otus.library.domain.Book;
import ru.epozin.otus.library.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {
    private final NamedParameterJdbcOperations jdbc;
    private final GenreDao genreDao;
    private final AuthorDao authorDao;

    @Override
    public void insert(Book book) {
        jdbc.update("insert into book (id, title, genre_id, author_id) values (:id, :title, :genreId, :authorId)", Map.of("id", book.getId(), "title", book.getTitle(), "genreId", book.getGenre().getId(), "authorId", book.getAuthor().getId()));
    }

    @Override
    public Optional<Book> getById(long id) {
        try {
            return Optional.ofNullable(jdbc.queryForObject("select b.id, b.title, b.author_id, b.genre_id from book b where b.id = :id", Map.of("id", id),
                    new BookMapper(genreDao, authorDao)));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> getAll() {

        return jdbc.query("select b.id, b.title, b.author_id, b.genre_id from book b", new BookMapper(genreDao, authorDao));
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from book where id = :id", Map.of("id", id));
    }

    private static class BookMapper implements RowMapper<Book> {
        private final GenreDao genreDao;
        private final AuthorDao authorDao;

        public BookMapper(GenreDao genreDao, AuthorDao authorDao) {
            this.genreDao = genreDao;
            this.authorDao = authorDao;
        }

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String title = resultSet.getString("title");
            Genre genre = null;
            Author author = null;
            genre = genreDao.getById(resultSet.getLong("genre_id")).orElse(null);
            author = authorDao.getById(resultSet.getLong("author_id")).orElse(null);

            return new Book(id, title, genre, author);
        }
    }
}
