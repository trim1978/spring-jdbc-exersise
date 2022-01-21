package ru.otus.trim.dao;

import ru.otus.trim.domain.Author;
import ru.otus.trim.domain.Book;
import ru.otus.trim.domain.Genre;

import java.util.List;

public interface LibraryDao {


    int getAuthorsCount();

    void insertAuthor(Author author);

    Author insertAuthor(String name);

    Author getAuthorById(int id);

    boolean deleteAuthorById(int id);

    List<Author> getAllAuthors();

    List<Genre> getAllAGenres();

    List<Book> getAllABooks();

    boolean deleteBookById(long id);

    int getBooksCount();

    void insertBook(Book book);
}
