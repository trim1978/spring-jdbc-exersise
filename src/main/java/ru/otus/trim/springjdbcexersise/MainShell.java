package ru.otus.trim.springjdbcexersise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile("shell")
@ComponentScan(value = "ru.otus.trim")
public class MainShell {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MainShell.class);
    }
}
