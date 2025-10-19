package ru.mirea.shutov.domain.repository;

import ru.mirea.shutov.domain.models.Movie;

public interface MovieRepository {
    public boolean saveMovie(Movie movie);
    public Movie getMovie();
}
