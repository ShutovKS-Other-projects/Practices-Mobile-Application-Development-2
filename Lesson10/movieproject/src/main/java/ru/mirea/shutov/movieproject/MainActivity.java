package ru.mirea.shutov.movieproject;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.shutov.data.repository.MovieRepositoryImpl;
import ru.mirea.shutov.data.storage.MovieStorage;
import ru.mirea.shutov.data.storage.sharedprefs.SharedPrefMovieStorage;
import ru.mirea.shutov.domain.models.Movie;
import ru.mirea.shutov.domain.usecases.GetFavoriteFilmUseCase;
import ru.mirea.shutov.domain.usecases.SaveMovieToFavoriteUseCase;
import ru.mirea.shutov.domain.repository.MovieRepository;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText text = findViewById(R.id.editTextMovie);
        TextView textView = findViewById(R.id.textViewMovie);

        MovieStorage storage = new SharedPrefMovieStorage(this);
        MovieRepository movieRepository = new MovieRepositoryImpl(storage);

        findViewById(R.id.buttonSaveMovie).setOnClickListener(view -> {
            Boolean result = new
                    SaveMovieToFavoriteUseCase(movieRepository).execute(new Movie(2,
                    text.getText().toString()));
            textView.setText(String.format("Save result %s", result));
        });

        findViewById(R.id.buttonGetMovie).setOnClickListener(view -> {
            Movie moview = new GetFavoriteFilmUseCase(movieRepository).execute();
            textView.setText(String.format("Save result %s", moview.getName()));
        });
    }
}