package ru.mirea.shutov.movieproject.presentation;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import ru.mirea.shutov.data.repository.MovieRepositoryImpl;
import ru.mirea.shutov.data.storage.sharedprefs.SharedPrefMovieStorage;
import ru.mirea.shutov.domain.repository.MovieRepository;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public ViewModelFactory(Context context) {
        this.context = context.getApplicationContext();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            SharedPrefMovieStorage storage = new SharedPrefMovieStorage(context);
            MovieRepository repository = new MovieRepositoryImpl(storage);
            return (T) new MainViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}