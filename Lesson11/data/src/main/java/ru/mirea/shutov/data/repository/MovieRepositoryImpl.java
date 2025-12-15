package ru.mirea.shutov.data.repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.mirea.shutov.data.storage.MovieStorage;
import ru.mirea.shutov.data.storage.models.Movie;
import ru.mirea.shutov.domain.repository.MovieRepository;

public class MovieRepositoryImpl implements MovieRepository {

    private final MovieStorage movieStorage;

    public MovieRepositoryImpl(MovieStorage movieStorage) {
        this.movieStorage = movieStorage;
    }

    @Override
    public boolean saveMovie(ru.mirea.shutov.domain.models.Movie movie) {
        Movie dataMovie = mapToStorage(movie);
        return movieStorage.save(dataMovie);
    }

    @Override
    public ru.mirea.shutov.domain.models.Movie getMovie() {
        Movie dataMovie = movieStorage.get();
        return mapToDomain(dataMovie);
    }

    private Movie mapToStorage(ru.mirea.shutov.domain.models.Movie domainMovie) {
        return new Movie(
                domainMovie.getId(),
                domainMovie.getName(),
                new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date())
        );
    }

    private ru.mirea.shutov.domain.models.Movie mapToDomain(Movie dataMovie) {
        return new ru.mirea.shutov.domain.models.Movie(
                dataMovie.getId(),
                dataMovie.getName()
        );
    }
}