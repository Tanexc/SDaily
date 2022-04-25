package ru.tanec.sdaily.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yariksoffice.lingver.Lingver;

import org.intellij.lang.annotations.Language;

import java.util.Calendar;
import java.util.Locale;

import ru.tanec.sdaily.services.NotificationService;
import ru.tanec.sdaily.R;
import ru.tanec.sdaily.adapters.items.RangeItem;
import ru.tanec.sdaily.database.DataBase;
import ru.tanec.sdaily.database.DataBaseApl;
import ru.tanec.sdaily.database.TimeTableDao;
import ru.tanec.sdaily.database.TimeTableEntity;

public class MainActivity extends AppCompatActivity {
    ImageButton language;
    TextView tatarLan;
    TextView russianLan;
    TextView englishLan;


    BottomNavigationView navigation;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Lingver.getInstance().setLocale(this, "tt");
        //Lingver.getInstance().setLocale(view1.getContext(),"ru");


        language = findViewById(R.id.language);
        tatarLan = findViewById(R.id.tt_lng);
        russianLan = findViewById(R.id.ru_lng);
        englishLan = findViewById(R.id.en_lng);

        tatarLan.setOnClickListener(l -> {
            tatarLan.setBackgroundResource(R.color.day_fill);
            russianLan.setBackgroundResource(R.color.fragment_background);
            englishLan.setBackgroundResource(R.color.fragment_background);
            Lingver.getInstance().setLocale(l.getContext(),"tt");
            ReloadActivity();
            tatarLan.setBackgroundResource(R.color.day_fill);
        });

        russianLan.setOnClickListener(l -> {
            russianLan.setBackgroundResource(R.color.day_fill);
            tatarLan.setBackgroundResource(R.color.fragment_background);
            englishLan.setBackgroundResource(R.color.fragment_background);
            Lingver.getInstance().setLocale(l.getContext(),"ru");
            ReloadActivity();
            russianLan.setBackgroundResource(R.color.day_fill);
        });

        englishLan.setOnClickListener(l -> {
            englishLan.setBackgroundResource(R.color.day_fill);
            russianLan.setBackgroundResource(R.color.fragment_background);
            tatarLan.setBackgroundResource(R.color.fragment_background);
            Lingver.getInstance().setLocale(l.getContext(),"en");
            ReloadActivity();
            englishLan.setBackgroundResource(R.color.day_fill);
        });

        language.setOnClickListener(view1 -> {

            if (tatarLan.getVisibility() == View.GONE) {
                language.setBackgroundResource(R.drawable.settings_b);
                tatarLan.setVisibility(View.VISIBLE);
                russianLan.setVisibility(View.VISIBLE);
                englishLan.setVisibility(View.VISIBLE);

            } else {
                language.setBackgroundResource(R.drawable.settings);
                tatarLan.setVisibility(View.GONE);
                russianLan.setVisibility(View.GONE);
                englishLan.setVisibility(View.GONE);
            }
        });
        navigation = findViewById(R.id.bottomNavigationView);
        navigation.setSelectedItemId(R.id.goal);
        NavController navController = Navigation.findNavController(this, R.id.nav_host);
        navigation.setOnItemSelectedListener(item -> {
            navController.popBackStack();
            navController.navigate(item.getItemId());
            return true;
        });
        Intent serviceIntent = new Intent(this, NotificationService.class);
        startService(serviceIntent);

        String[] id_title = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        new Thread(() -> {
            DataBase db = DataBaseApl.instance.getDatabase();
            TimeTableDao td = db.timeTableDao();

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.HOUR, 0);
            cal.set(Calendar.MILLISECOND, 0);

            for (int i = 0; i < 7; i++) {
                if (td.getById(i) == null) {
                    TimeTableEntity te = new TimeTableEntity();
                    te.timerange = new RangeItem[0];
                    te.title = id_title[i];
                    te.id = i;
                    td.insert(te);
                }
            }
        }).start();

    }

    private void ReloadActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}