package ru.otus.trim.dao;

import ru.otus.trim.domain.Author;

import java.util.List;

public interface AuthorDao {
    Author insertAuthor(String name);

    Author getAuthorById(int id);

    boolean deleteAuthorById(int id);

    List<Author> getAllAuthors();

}
