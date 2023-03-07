package ru.epozin.otus.library.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;
import ru.epozin.otus.library.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GenreDaoImpl implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public void insert(Genre genre) {
        jdbc.update("insert into genre (id, name) values (:id, :name)", Map.of("id", genre.getId(), "name", genre.getName()));
    }

    @Override
    public Optional<Genre> getById(long id) {
        try {
            return Optional.ofNullable(jdbc.queryForObject("select g.id, g.name, from genre g where g.id = :id", Map.of("id", id),
                    new GenreMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select g.id, g.name, from genre g", new GenreMapper());
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from genre where id = :id", Map.of("id", id));
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }
}
