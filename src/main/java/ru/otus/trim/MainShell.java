package ru.otus.trim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile("shell")
public class MainShell {

    public static void main(String[] args) {
        SpringApplication.run(MainShell.class);
    }
}
