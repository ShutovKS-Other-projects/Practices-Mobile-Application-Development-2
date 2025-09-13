package ru.mirea.shutov.movieproject;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.shutov.movieproject.data.repository.MovieRepositoryImpl;
import ru.mirea.shutov.movieproject.domain.models.Movie;
import ru.mirea.shutov.movieproject.domain.usecases.GetFavoriteFilmUseCase;
import ru.mirea.shutov.movieproject.domain.usecases.SaveMovieToFavoriteUseCase;
import ru.mirea.shutov.movieproject.domain.repository.MovieRepository;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText text = findViewById(R.id.editTextMovie);
        TextView textView = findViewById(R.id.textViewMovie);
        MovieRepository movieRepository = new MovieRepositoryImpl(this);
        findViewById(R.id.buttonSaveMovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean result = new
                        SaveMovieToFavoriteUseCase(movieRepository).execute(new Movie(2,
                        text.getText().toString()));
                textView.setText(String.format("Save result %s", result));
            }
        });
        findViewById(R.id.buttonGetMovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Movie moview = new GetFavoriteFilmUseCase(movieRepository).execute();
                textView.setText(String.format("Save result %s", moview.getName()));
            }
        });
    }
}