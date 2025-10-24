package ru.mirea.shutov.retrofitapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.mirea.shutov.retrofitapp.api.ApiService;
import ru.mirea.shutov.retrofitapp.model.Todo;

import com.squareup.picasso.Picasso;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private List<Todo> todoList;
    private ApiService apiService;

    public TodoAdapter(List<Todo> todoList, ApiService apiService) {
        this.todoList = todoList;
        this.apiService = apiService;
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        Todo todo = todoList.get(position);
        holder.textViewTitle.setText(todo.getTitle());

        holder.checkBoxCompleted.setOnCheckedChangeListener(null);
        holder.checkBoxCompleted.setChecked(Boolean.TRUE.equals(todo.getCompleted()));

        holder.checkBoxCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int adapterPosition = holder.getBindingAdapterPosition();
            if (adapterPosition == RecyclerView.NO_POSITION) {
                return;
            }

            Todo updatedTodo = new Todo(isChecked);

            Call<Todo> call = apiService.updateTodo(todo.getId(), updatedTodo);
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<Todo> call, Response<Todo> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d("TodoAdapter", "Todo " + todo.getId() + " updated successfully!");
                        todoList.get(adapterPosition).setCompleted(response.body().getCompleted());
                        notifyItemChanged(adapterPosition);
                    } else {
                        Log.e("TodoAdapter", "Failed to update todo. Code: " + response.code());
                        buttonView.setChecked(!isChecked);
                    }
                }

                @Override
                public void onFailure(Call<Todo> call, Throwable t) {
                    Log.e("TodoAdapter", "Network error on update: " + t.getMessage());
                    buttonView.setChecked(!isChecked);
                }
            });
        });

        String imageUrl = "https://www.gravatar.com/avatar/" + todo.getUserId() + "?d=identicon";

        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(holder.imageViewAvatar);
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public static class TodoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewAvatar;
        TextView textViewTitle;
        CheckBox checkBoxCompleted;

        public TodoViewHolder(View itemView) {
            super(itemView);
            imageViewAvatar = itemView.findViewById(R.id.imageViewAvatar);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            checkBoxCompleted = itemView.findViewById(R.id.checkBoxCompleted);
        }
    }
}