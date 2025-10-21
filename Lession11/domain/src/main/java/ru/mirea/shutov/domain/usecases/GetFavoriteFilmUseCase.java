package ru.mirea.shutov.domain.usecases;

import ru.mirea.shutov.domain.models.Movie;
import ru.mirea.shutov.domain.repository.MovieRepository;

public class GetFavoriteFilmUseCase {
    private MovieRepository movieRepository;

    public GetFavoriteFilmUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie execute() {
        return movieRepository.getMovie();
    }
}