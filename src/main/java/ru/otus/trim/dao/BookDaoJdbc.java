package ru.otus.trim.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.trim.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final AuthorDaoJdbc authorDao;
    private final GenreDaoJdbc genreDao;

    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations, AuthorDaoJdbc authorDao, GenreDaoJdbc genreDao) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public Book insertBook(String title, int authorId, int genreId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", title);
        params.addValue("author", authorId);
        params.addValue("genre", genreId);
        KeyHolder kh = new GeneratedKeyHolder();
        namedParameterJdbcOperations.update("INSERT INTO books(title,author,genre) VALUES (':title',:author,:genre)", params, kh);
        return new Book(kh.getKey().longValue(), title, authorDao.getAuthorById(authorId), genreDao.getGenreById(genreId));
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
        return namedParameterJdbcOperations.query("select id, title, author, genre from books", new BookMapper());
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
