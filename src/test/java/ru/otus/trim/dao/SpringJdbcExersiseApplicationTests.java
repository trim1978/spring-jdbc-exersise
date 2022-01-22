package ru.otus.trim.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.trim.domain.Author;
import ru.otus.trim.domain.Book;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Test Books CRUD")
@Profile("console")
@ComponentScan(basePackages = "ru.otus.trim")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LibraryDao.class, LibraryDaoJdbc.class})
class SpringJdbcExersiseApplicationTests {

	private static final String AUTHOR_PUSHKIN = "Pushkin";
	private static final String GENRE_DRAMA = "drama";
	private static final String TITLE_1 = "Metel";
	@Autowired
	private LibraryDao library;

	@BeforeEach
	void init() {
		library.insertAuthor(AUTHOR_PUSHKIN);
	}

	@AfterEach
	void dispose() {
		for (Author a : library.getAllAuthors())
			if (a.getName().equalsIgnoreCase(AUTHOR_PUSHKIN)) {
				library.deleteAuthorById(a.getId());
				break;
			}
	}

	@DisplayName("insert")
	@Test
	void countTest() {
		Book book = library.insertBook(TITLE_1, AUTHOR_PUSHKIN, GENRE_DRAMA);
		assertThat(book.getTitle()).isEqualTo(TITLE_1);
		assertThat(book.getAuthor().getName()).isEqualTo(AUTHOR_PUSHKIN);
		assertThat(book.getGenre().getName()).isEqualTo(GENRE_DRAMA);
	}

}
