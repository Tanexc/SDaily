package ru.tanec.sdaily.database;

import android.app.Application;
import androidx.room.Room;

import com.yariksoffice.lingver.Lingver;

public class DataBaseApl extends Application {

    public static DataBaseApl instance;

    private DataBase database;

    @Override
    public void onCreate() {
        super.onCreate();
        Lingver.init(this);
        instance = this;
        database = Room.databaseBuilder(this, DataBase.class, "database").build();
    }

    public DataBase getDatabase() {
        return database;
    }
}
