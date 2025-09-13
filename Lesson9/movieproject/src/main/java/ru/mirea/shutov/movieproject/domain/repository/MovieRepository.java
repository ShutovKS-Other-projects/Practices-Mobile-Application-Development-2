package ru.mirea.shutov.movieproject.domain.repository;

import ru.mirea.shutov.movieproject.domain.models.Movie;

public interface MovieRepository {
    public boolean saveMovie(Movie movie);
    public Movie getMovie();
}
