package ru.otus.trim;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.trim.dao.BookDao;
import ru.otus.trim.domain.Book;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Test Books CRUD")
@ComponentScan(basePackages = "ru.otus.trim")
@SpringBootTest
class BookDaoTests {

    private static final String AUTHOR_PUSHKIN = "Pushkin";
    private static final String GENRE_DRAMA = "drama";
    private static final String TITLE_1 = "Metel";
    @Autowired
    private BookDao library;

    @DisplayName("insert")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void insertTest() {
        Book book = library.insertBook(TITLE_1, 1, 6);
        assertThat(book.getTitle()).isEqualTo(TITLE_1);
        assertThat(book.getAuthor().getName()).isEqualTo(AUTHOR_PUSHKIN);
        assertThat(book.getGenre().getName()).isEqualTo(GENRE_DRAMA);
        assertThat(library.getBookById(book.getId())).isEqualTo(book);
    }

    @DisplayName("update")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void updateTest() {
        Book book = library.insertBook(TITLE_1, 1, 6);
        library.updateBookById(book.getId(), 4);
        assertThat(library.getBookById(book.getId()).getGenre().getName()).isEqualTo("lyric");
    }

    @DisplayName("delete")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void deleteTest() {
        Book book = library.insertBook(TITLE_1, 1, 6);
        library.deleteBookById(book.getId());
        assertThat(library.getBookById(book.getId())).isNull();
    }

    @DisplayName("select")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void selectTest() {
        Book book = library.insertBook(TITLE_1, 1, 6);
        assertThat(library.getAllBooks()).isNotEmpty();
    }
}
