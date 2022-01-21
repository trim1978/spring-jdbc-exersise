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

    Genre getGenreById(int id);

    List<Genre> getAllAGenres();

    List<Book> getAllBooks();

    boolean deleteBookById(long id);

    int getBooksCount();

    Book insertBook(String title, String author, String genre);
}
