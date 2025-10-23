package ru.mirea.shutov.listviewapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private String[] readingList = new String[]{
            "1. '1984' - Джордж Оруэлл",
            "2. 'Мастер и Маргарита' - Михаил Булгаков",
            "3. 'Преступление и наказание' - Фёдор Достоевский",
            "4. 'Война и мир' - Лев Толстой",
            "5. 'Сто лет одиночества' - Габриэль Гарсиа Маркес",
            "6. 'Над пропастью во ржи' - Джером Сэлинджер",
            "7. 'Улисс' - Джеймс Джойс",
            "8. 'Гордость и предубеждение' - Джейн Остин",
            "9. 'Великий Гэтсби' - Фрэнсис Скотт Фицджеральд",
            "10. 'Лолита' - Владимир Набоков",
            "11. 'Процесс' - Франц Кафка",
            "12. 'Божественная комедия' - Данте Алигьери",
            "13. 'Гамлет' - Уильям Шекспир",
            "14. 'Дон Кихот' - Мигель де Сервантес",
            "15. 'Моби Дик' - Герман Мелвилл",
            "16. 'О дивный новый мир' - Олдос Хаксли",
            "17. 'Приключения Гекльберри Финна' - Марк Твен",
            "18. 'Сердце тьмы' - Джозеф Конрад",
            "19. 'Цветы для Элджернона' - Дэниел Киз",
            "20. '451 градус по Фаренгейту' - Рэй Брэдбери",
            "21. 'Автостопом по галактике' - Дуглас Адамс",
            "22. 'Посторонний' - Альбер Камю",
            "23. 'Заводной апельсин' - Энтони Бёрджесс",
            "24. 'Бойня номер пять' - Курт Воннегут",
            "25. 'Имя розы' - Умберто Эко",
            "26. 'Солярис' - Станислав Лем",
            "27. 'Пикник на обочине' - Аркадий и Борис Стругацкие",
            "28. 'Дюна' - Фрэнк Герберт",
            "29. 'Властелин колец' - Дж.Р.Р. Толкин",
            "30. 'Игра в бисер' - Герман Гессе",
            "31. 'Человек-невидимка' - Герберт Уэллс"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.list_view);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                readingList
        );

        listView.setAdapter(adapter);
    }
}