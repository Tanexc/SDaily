package ru.tanec.sdaily.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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

//        class LocaleManager {
//
//            public void setLocale(Context c) {
//                setNewLocale(c, getLanguage(c));
//            }
//
//            public void setNewLocale(Context c, String language) {
//                persistLanguage(c, language);
//                updateResources(c, language);
//            }
//
//            public String getLanguage(Context c){
//
//                return null;
//            }
//
////            private void persistLanguage(Context c, String language) { ... }
//
//            private void updateResources(Context context, String language) {
//                Locale locale = new Locale(language);
//                Locale.setDefault(locale);
//
//                Resources res = context.getResources();
//                Configuration config = new Configuration(res.getConfiguration());
//                config.locale = locale;
//                res.updateConfiguration(config, res.getDisplayMetrics());
//            }
//        }
   }
}