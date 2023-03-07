package ru.epozin.otus.library.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;
import ru.epozin.otus.library.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public void insert(Author author) {
        jdbc.update("insert into author (id, full_name) values (:id, :fullName)", Map.of("id", author.getId(), "fullName", author.getFullName()));
    }

    @Override
    public Optional<Author> getById(long id) {
        try {
            return Optional.ofNullable(jdbc.queryForObject("select a.id, a.full_name, from author a where a.id = :id", Map.of("id", id),
                    new authorMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select a.id, a.full_name, from author a", new authorMapper());
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from author where id = :id", Map.of("id", id));
    }

    private static class authorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String fullName = resultSet.getString("full_name");
            return new Author(id, fullName);
        }
    }
}
