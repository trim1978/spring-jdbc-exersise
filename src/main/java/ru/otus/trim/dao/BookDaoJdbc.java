package ru.otus.trim.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.trim.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

@Repository
public class BookDaoJdbc implements BookDao {

    private final JdbcOperations jdbc;
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final AuthorDaoJdbc authorDao;
    private final GenreDaoJdbc genreDao;

    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations, AuthorDaoJdbc authorDao, GenreDaoJdbc genreDao) {
        this.jdbc = namedParameterJdbcOperations.getJdbcOperations();
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public Book insertBook(String title, int authorId, int genreId) {
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
        //int authorId = authorDao.getAuthorByName(author).getId();
        //int genreId = genreDao.getGenreByName(genre).getId();

        var psc = pscf.newPreparedStatementCreator(List.of(title, authorId, genreId));

        var keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, keyHolder);

        int id = Objects.requireNonNull(keyHolder.getKey()).intValue();
        return new Book(id, title, authorDao.getAuthorById(authorId), genreDao.getGenreById(genreId));
    }

    @Override
    public Book getBookById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForStream(
                "select id, title, author, genre from books where id = :id", params, new BookMapper()
        ).findAny().orElse(null);
    }

    @Override
    public List<Book> getAllBooks() {
        return jdbc.query("select id, title, author, genre from books", new BookMapper());
    }

    @Override
    public boolean deleteBookById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.update(
                "delete from books where id = :id", params
        ) > 0;
    }

    @Override
    public void updateBookById(long id, int genre) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("id", id);
        params.put("genre", genre);
        namedParameterJdbcOperations.update(
                "update books set genre=:genre where id = :id", params
        );
    }

    private class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            int author = resultSet.getInt("author");
            int genre = resultSet.getInt("genre");
            return new Book(id, title, authorDao.getAuthorById(author), genreDao.getGenreById(genre));
        }
    }
}
