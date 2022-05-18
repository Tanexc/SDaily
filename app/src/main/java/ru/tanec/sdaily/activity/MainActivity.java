package ru.tanec.sdaily.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yariksoffice.lingver.Lingver;

import java.util.Calendar;

import ru.tanec.sdaily.R;
import ru.tanec.sdaily.adapters.items.RangeItem;
import ru.tanec.sdaily.database.DataBase;
import ru.tanec.sdaily.database.DataBaseApl;
import ru.tanec.sdaily.database.TimeTableDao;
import ru.tanec.sdaily.database.TimeTableEntity;
import ru.tanec.sdaily.services.NotificationService;

public class MainActivity extends AppCompatActivity {
    public BottomNavigationView navigation;
    public NavController navController;

    ImageView language;
    ImageView tatarLan;
    ImageView russianLan;
    ImageView englishLan;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = findViewById(R.id.bottomNavigationView);
        navController = Navigation.findNavController(this, R.id.nav_host);
        try {
            navigation.setSelectedItemId(savedInstanceState.getInt("nav"));
            navController.navigate(savedInstanceState.getInt("nav"));
        } catch (Exception e) {
            navigation.setSelectedItemId(R.id.task);
            navController.navigate(R.id.task);
        }
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


        language = findViewById(R.id.language);
        tatarLan = findViewById(R.id.tt_lng);
        russianLan = findViewById(R.id.ru_lng);
        englishLan = findViewById(R.id.en_lng);

        tatarLan.setOnClickListener(l -> {
            Lingver.getInstance().setLocale(l.getContext(),"tt");
            recreate();
            tatarLan.setBackgroundResource(R.color.md_theme_light_onPrimaryContainer);
        });

        russianLan.setOnClickListener(l -> {
            Lingver.getInstance().setLocale(l.getContext(),"ru");
            recreate();
            russianLan.setBackgroundResource(R.color.md_theme_light_onPrimaryContainer);
        });

        englishLan.setOnClickListener(l -> {
            Lingver.getInstance().setLocale(l.getContext(),"en");
            recreate();
            englishLan.setBackgroundResource(R.color.md_theme_light_onPrimaryContainer);
        });

        language.setOnClickListener(view1 -> {
            language.setImageResource(R.drawable.settings_b);
            if (tatarLan.getVisibility() == View.GONE) {
                language.setImageResource(R.drawable.settings_b);
                ImageViewCompat.setImageTintList(language, ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_theme_light_primary)));
                tatarLan.setVisibility(View.VISIBLE);
                russianLan.setVisibility(View.VISIBLE);
                englishLan.setVisibility(View.VISIBLE);

            } else {
                language.setImageResource(R.drawable.settings);
                ImageViewCompat.setImageTintList(language, ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_theme_light_shadow)));
                tatarLan.setVisibility(View.GONE);
                russianLan.setVisibility(View.GONE);
                englishLan.setVisibility(View.GONE);
            }
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("nav", navController.getCurrentDestination().getId());
    }

    private void ReloadActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}