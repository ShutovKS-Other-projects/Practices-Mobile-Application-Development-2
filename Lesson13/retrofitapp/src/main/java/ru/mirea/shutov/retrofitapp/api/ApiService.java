package ru.mirea.shutov.retrofitapp.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import ru.mirea.shutov.retrofitapp.model.Todo;

public interface ApiService {
    @GET("todos")
    Call<List<Todo>> getTodos();

    @PATCH("todos/{id}")
    Call<Todo> updateTodo(
            @Path("id") int todoId,
            @Body Todo todo
    );
}