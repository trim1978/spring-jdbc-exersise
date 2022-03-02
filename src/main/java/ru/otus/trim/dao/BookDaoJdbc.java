package ru.otus.trim.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.trim.domain.Author;
import ru.otus.trim.domain.Book;
import ru.otus.trim.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
        namedParameterJdbcOperations.update("INSERT INTO books(title,author,genre) VALUES (:title,:author,:genre)", params, kh);
        return new Book(Objects.requireNonNull(kh.getKey()).longValue(), title, authorDao.getAuthorById(authorId), genreDao.getGenreById(genreId));
    }

    private static final String BOOK_REQUEST = "select book.id id, book.title title, book.author author_id, book.genre genre_id, author.name author_name, genre.name genre_name from books book left join authors author on author.id = book.author left join genres genre on genre.id = book.genre";

    @Override
    public Book getBookById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForStream(
                BOOK_REQUEST + " where book.id = :id", params, new BookMapper()
        ).findAny().orElse(null);
    }

    @Override
    public List<Book> getAllBooks() {
        return namedParameterJdbcOperations.query(BOOK_REQUEST, new BookMapper());
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

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            int author_id = resultSet.getInt("author_id");
            int genre_id = resultSet.getInt("genre_id");
            String author_name = resultSet.getString("author_name");
            String genre_name = resultSet.getString("genre_name");
            return new Book(id, title, new Author(author_id, author_name), new Genre(genre_id, genre_name));
        }
    }
}
