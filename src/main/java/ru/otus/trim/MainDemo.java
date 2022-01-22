package ru.otus.trim;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import ru.otus.trim.domain.Author;
import ru.otus.trim.service.LibraryService;

@SpringBootApplication
@Profile("console")
public class MainDemo {

    public static void main(String[] args) throws Exception {


        ApplicationContext context = SpringApplication.run(MainDemo.class, args);

        LibraryService service = context.getBean(LibraryService.class);

        //System.out.println("All count " + ervice.library.getAuthorsCount());

        service.library.insertAuthor("Lermontov");

        //System.out.println("All count " + ervice.library.getAuthorsCount());

        Author author = service.library.getAuthorById(2);

		System.out.println("Ivan id: " + author.getId() + " name: " + author.getName());

		Console.main(args);
	}

}
