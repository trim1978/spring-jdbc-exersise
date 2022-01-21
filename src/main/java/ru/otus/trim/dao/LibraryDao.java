package ru.otus.trim.dao;

import ru.otus.trim.domain.Author;
import ru.otus.trim.domain.Book;
import ru.otus.trim.domain.Genre;

import java.util.List;

public interface LibraryDao {


    int getAuthorsCount();
    void insertAuthor(Author person);
    Author getAuthorById(int id);
    boolean deleteAuthorById(int id);

    List<Author> getAllAuthors();
    List<Genre> getAllAGenres();

    List<Book> getAllABooks();
    int getBooksCount();

}
