package ru.mirea.shutov.movieproject.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mirea.shutov.domain.models.Movie;
import ru.mirea.shutov.domain.repository.MovieRepository;
import ru.mirea.shutov.domain.usecases.GetFavoriteFilmUseCase;
import ru.mirea.shutov.domain.usecases.SaveMovieToFavoriteUseCase;

public class MainViewModel extends ViewModel {

    private final SaveMovieToFavoriteUseCase saveUseCase;
    private final GetFavoriteFilmUseCase getUseCase;

    private final MutableLiveData<String> favoriteMovie = new MutableLiveData<>();

    public LiveData<String> getFavoriteMovie() {
        return favoriteMovie;
    }

    public MainViewModel(MovieRepository movieRepository) {
        this.saveUseCase = new SaveMovieToFavoriteUseCase(movieRepository);
        this.getUseCase = new GetFavoriteFilmUseCase(movieRepository);
    }

    public void saveMovie(String movieName) {
        boolean result = saveUseCase.execute(new Movie(1, movieName));
        favoriteMovie.setValue("Save result: " + result);
    }

    public void loadMovie() {
        Movie movie = getUseCase.execute();
        favoriteMovie.setValue(movie.getName());
    }
}