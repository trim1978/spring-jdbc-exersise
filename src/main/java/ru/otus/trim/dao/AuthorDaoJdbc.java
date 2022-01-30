package ru.otus.trim.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.trim.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final JdbcOperations jdbc;
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public AuthorDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbc = namedParameterJdbcOperations.getJdbcOperations();
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public Author insertAuthor(final String name) {
        Author author = getAuthorByName(name);
        if (author != null) {
            return author;
        }
        String sql = "INSERT INTO authors(name) VALUES (?)";

        var decParams = List.of(
                new SqlParameter(Types.VARCHAR, "name"));

        var pscf = new PreparedStatementCreatorFactory(sql, decParams) {
            {
                setReturnGeneratedKeys(true);
                setGeneratedKeysColumnNames("id");
            }
        };

        var psc = pscf.newPreparedStatementCreator(List.of(name));

        var keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, keyHolder);

        int id = Objects.requireNonNull(keyHolder.getKey()).intValue();
        return new Author(id, name);
    }

    @Override
    public Author getAuthorById(int id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "select id, name from authors where id = :id", params, new AuthorMapper()
        );
    }

    Author getAuthorByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        return namedParameterJdbcOperations.queryForStream(
                "select id, name from authors where name = :name", params, new AuthorMapper()
        ).findAny().orElse(null);
        //return authors.stream().getAny();
    }

    @Override
    public List<Author> getAllAuthors() {
        return jdbc.query("select id, name from authors", new AuthorMapper());
    }

    @Override
    public boolean deleteAuthorById(int id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from authors where id = :id", params
        );
        return true;
    }

    static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            return new Author(id, name);
        }
    }
}
