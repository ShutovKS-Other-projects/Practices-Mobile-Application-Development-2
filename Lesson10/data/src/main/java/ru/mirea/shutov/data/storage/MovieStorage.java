package ru.mirea.shutov.data.storage;

import ru.mirea.shutov.data.storage.models.Movie;

public interface MovieStorage {
    boolean save(Movie movie);
    Movie get();
}