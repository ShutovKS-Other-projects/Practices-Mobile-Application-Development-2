package ru.mirea.shutov.scrollviewapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout container = findViewById(R.id.linear_container);
        LayoutInflater inflater = getLayoutInflater();

        long currentValue = 1;

        for (int i = 1; i <= 100; i++) {
            View itemView = inflater.inflate(R.layout.item_layout, container, false);

            TextView itemNumber = itemView.findViewById(R.id.item_number);
            TextView itemValue = itemView.findViewById(R.id.item_value);

            itemNumber.setText(i + ".");
            itemValue.setText(String.valueOf(currentValue));

            container.addView(itemView);

            currentValue *= 2;
        }
    }
}