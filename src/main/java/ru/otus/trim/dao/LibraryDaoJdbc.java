package ru.otus.trim.dao;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.trim.domain.Author;
import ru.otus.trim.domain.Book;
import ru.otus.trim.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@Profile({"shell", "console"})
public class LibraryDaoJdbc implements LibraryDao {

    private final JdbcOperations jdbc;
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public LibraryDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations)
    {
        this.jdbc = namedParameterJdbcOperations.getJdbcOperations();
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    @Transactional(readOnly = true)
    public int getBooksCount() {
        Integer count = jdbc.queryForObject("select count(*) from books", Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    @Transactional(readOnly = true)
    public Book insertBook(String title, String author, String genre) {
//        namedParameterJdbcOperations.update("insert into books (id, name) values (:id, :name, :author, :genre)",
//                Map.of("id", book.getId(), "name", book.getName(), "author", book.getAuthor().getId(), "genre", book.getGenre().getId()));
        String sql = "INSERT INTO books(title,author,genre) VALUES (?,?,?)";

        var decParams = List.of(
                new SqlParameter(Types.VARCHAR, "title"), new SqlParameter(Types.INTEGER, "author"), new SqlParameter(Types.INTEGER, "genre"));

        var pscf = new PreparedStatementCreatorFactory(sql, decParams) {
            {
                setReturnGeneratedKeys(true);
                setGeneratedKeysColumnNames("id");
            }
        };
        int authorId = getAuthorByName(author).getId();
        int genreId = getGenreByName(genre).getId();

        var psc = pscf.newPreparedStatementCreator(List.of(title, authorId, genreId));

        var keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, keyHolder);

        int id = Objects.requireNonNull(keyHolder.getKey()).intValue();
        return new Book(id, title, new Author(authorId, author), new Genre(genreId, genre));
    }

    @Override
    @Transactional(readOnly = true)
    public int getAuthorsCount() {
        Integer count = jdbc.queryForObject("select count(*) from authors", Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public void insertAuthor(Author author) {
        namedParameterJdbcOperations.update("insert into authors (id, name) values (:id, :name)",
                Map.of("id", author.getId(), "name", author.getName()));
    }

    @Override
    public Author insertAuthor(final String name) {
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
    @Transactional(readOnly = true)
    public Author getAuthorById(int id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "select id, name from authors where id = :id", params, new AuthorMapper()
        );
    }

    private Author getAuthorByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        Author author = namedParameterJdbcOperations.queryForObject(
                "select id, name from authors where name = :name", params, new AuthorMapper()
        );
        if (author == null) author = insertAuthor(name);
        return author;
    }

    private Genre getGenreByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        return namedParameterJdbcOperations.queryForObject(
                "select id, name from genres where name = :name", params, new GenreMapper()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<Author> getAllAuthors() {
        return jdbc.query("select id, name from authors", new AuthorMapper());
    }

    @Override
    @Transactional(readOnly = true)
    public Genre getGenreById(int id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "select id, name from genres where id = :id", params, new GenreMapper()
        );
    }

    @Override
    public List<Genre> getAllAGenres() {
        return jdbc.query("select id, name from genres", new GenreMapper());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return jdbc.query("select id, title, author, genre from books", new BookMapper());
    }

    @Override
    public boolean deleteAuthorById(int id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from authors where id = :id", params
        );
        return true;
    }

    @Override
    public boolean deleteBookById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from books where id = :id", params
        );
        return true;
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            return new Author(id, name);
        }
    }

    private class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            int author = resultSet.getInt("author");
            int genre = resultSet.getInt("genre");
            return new Book(id, title, getAuthorById(author), getGenreById(genre));
        }
    }
}
