package ru.otus.trim.dao;

import ru.otus.trim.domain.Genre;

import java.util.List;

public interface GenreDao {
    Genre getGenreById(int id);

    List<Genre> getAllGenres();
}
