package ru.tanec.sdaily;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = findViewById(R.id.bottomNavigationView);
        navigation.setSelectedItemId(R.id.goal);
        NavController navController = Navigation.findNavController(this, R.id.nav_host);
        navigation.setOnItemSelectedListener(item -> {
            navController.navigate(item.getItemId());
            return true;
        });

        String[] id_title = new String[]{"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"};
        new Thread(() -> {
            DataBase db = DataBaseApl.getInstance().getDatabase();
            TimeTableDao td = db.timeTableDao();
            if (td.getById(0) == null) {
                for (int i = 0; i < 7; i++) {
                    TimeTableEntity te = new TimeTableEntity();
                    te.timerange = new RangeItem[0];
                    te.title = id_title[i];
                    te.id = i;
                    td.insert(te);
                }
            }
        }).start();
    }
}