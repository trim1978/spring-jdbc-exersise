package ru.otus.trim.springjdbcexersise;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.trim.dao.LibraryDao;
import ru.otus.trim.domain.Author;

@SpringBootApplication
@ComponentScan("ru.otus.trim")
public class SpringJdbcExersiseApplication {

	public static void main(String[] args) throws Exception{


		ApplicationContext context = SpringApplication.run(SpringJdbcExersiseApplication.class, args);

		LibraryDao dao = context.getBean(LibraryDao.class);

		System.out.println("All count " + dao.getAuthorsCount());

		dao.insertAuthor(new Author(2, "Lermontov"));

		System.out.println("All count " + dao.getAuthorsCount());

		Author author = dao.getAuthorById(2);

		System.out.println("Ivan id: " + author.getId() + " name: " + author.getName());

		Console.main(args);
	}

}
