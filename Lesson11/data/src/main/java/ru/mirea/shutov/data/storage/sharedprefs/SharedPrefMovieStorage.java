package ru.mirea.shutov.data.storage.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

import ru.mirea.shutov.data.storage.MovieStorage;
import ru.mirea.shutov.data.storage.models.Movie;

public class SharedPrefMovieStorage implements MovieStorage {

    private static final String PREFERENCES_NAME = "movie_prefs";
    private static final String KEY_MOVIE_NAME = "favorite_movie_name";
    private static final String KEY_MOVIE_DATE = "favorite_movie_date";

    private final SharedPreferences sharedPreferences;

    public SharedPrefMovieStorage(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean save(Movie movie) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_MOVIE_NAME, movie.getName());
        editor.putString(KEY_MOVIE_DATE, movie.getSaveDate());
        return editor.commit();
    }

    @Override
    public Movie get() {
        String movieName = sharedPreferences.getString(KEY_MOVIE_NAME, "Нет сохраненного фильма");
        String movieDate = sharedPreferences.getString(KEY_MOVIE_DATE, "Неизвестная дата");

        return new Movie(1, movieName, movieDate);
    }
}