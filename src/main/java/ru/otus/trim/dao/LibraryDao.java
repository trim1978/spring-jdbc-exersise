package ru.otus.trim.dao;

import ru.otus.trim.domain.Author;

import java.util.List;

public interface LibraryDao {

    int count();

    int getAuthorsCount();

    void insertAuthor(Author person);

    Author getAuthorById(int id);

    List<Author> getAllAuthors();

    void deleteAuthorById(int id);
}
