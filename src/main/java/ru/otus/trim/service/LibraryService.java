package ru.otus.trim.service;

import ru.otus.trim.domain.Author;
import ru.otus.trim.domain.Book;
import ru.otus.trim.domain.Genre;

import java.util.List;

public interface LibraryService {
    Book setBook(Book book);

    Book removeBookById(long bookID);

    Book getBookById(long bookID);

    List<Book> getBooks();

    Author getAuthor(String name);

    Genre getGenre(String name);

    List<Author> getAuthors();

    List<Genre> getGenres();
}
