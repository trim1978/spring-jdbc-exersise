package ru.otus.trim;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile("console")
public class MainConsole {

    public static void main(String[] args) throws Exception {

        ApplicationContext context = SpringApplication.run(MainConsole.class);

        Console.main(args);
    }
}
