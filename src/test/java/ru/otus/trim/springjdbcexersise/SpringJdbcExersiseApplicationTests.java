package ru.otus.trim.springjdbcexersise;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import ru.otus.trim.dao.LibraryDao;

@DisplayName("Тест CRUD")
@Profile("console")
@ComponentScan(basePackages = "ru.otus.trim")
@SpringBootTest(classes = LibraryDao.class)
class SpringJdbcExersiseApplicationTests {

	@Test
	void contextLoads() {
	}

}
