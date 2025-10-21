package ru.mirea.shutov.movieproject;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.shutov.movieproject.presentation.MainViewModel;
import ru.mirea.shutov.movieproject.presentation.ViewModelFactory;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;
    private EditText textInput;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel = new ViewModelProvider(this,
                new ViewModelFactory(this)).get(MainViewModel.class);

        textInput = findViewById(R.id.editTextMovie);
        textView = findViewById(R.id.textViewMovie);

        mainViewModel.getFavoriteMovie().observe(this, movieText ->
                textView.setText(movieText));

        findViewById(R.id.buttonSaveMovie).setOnClickListener(v ->
                mainViewModel.saveMovie(textInput.getText().toString()));

        findViewById(R.id.buttonGetMovie).setOnClickListener(v ->
                mainViewModel.loadMovie());
    }
}