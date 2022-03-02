package ru.otus.trim;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.trim.dao.AuthorDaoJdbc;
import ru.otus.trim.dao.BookDao;
import ru.otus.trim.dao.BookDaoJdbc;
import ru.otus.trim.dao.GenreDaoJdbc;
import ru.otus.trim.domain.Book;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Test Books CRUD")
@JdbcTest
@Import({BookDaoJdbc.class, AuthorDaoJdbc.class, GenreDaoJdbc.class})
class BookDaoTests {

    @Autowired
    private BookDao library;

    @DisplayName("insert")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void insertTest() {
        Book book = library.insertBook("Metel", 1, 6);
        assertThat(book.getTitle()).isEqualTo("Metel");
        assertThat(book.getAuthor().getName()).isEqualTo("Pushkin");
        assertThat(book.getGenre().getName()).isEqualTo("drama");
        assertThat(library.getBookById(book.getId())).isEqualTo(book);
    }

    @DisplayName("update")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void updateTest() {
        Book book = library.insertBook("Metel", 1, 6);
        library.updateBookById(book.getId(), 4);
        assertThat(library.getBookById(book.getId()).getGenre().getName()).isEqualTo("lyric");
    }

    @DisplayName("delete")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void deleteTest() {
        Book book = library.insertBook("Metel", 1, 6);
        library.deleteBookById(book.getId());
        assertThat(library.getBookById(book.getId())).isNull();
    }

    @DisplayName("select")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void selectTest() {
        Book book = library.insertBook("Metel", 1, 6);
        assertThat(library.getAllBooks()).isNotEmpty();
    }
}
