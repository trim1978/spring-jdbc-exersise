package ru.otus.trim.springjdbcexersise;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MainConsole {

    public static void main(String[] args) throws Exception {

        ApplicationContext context = SpringApplication.run(MainConsole.class);

        Console.main(args);
    }
}
