package ru.tanec.sdaily.database;

import android.app.Application;
import androidx.room.Room;

public class DataBaseApl extends Application {

    public static DataBaseApl instance;

    private DataBase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, DataBase.class, "database").build();
    }

    public static DataBaseApl getInstance() {
        return instance;
    }

    public DataBase getDatabase() {
        return database;
    }
}
