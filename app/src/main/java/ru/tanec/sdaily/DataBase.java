package ru.tanec.sdaily;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


@Database(entities = {TimeTableEntity.class}, version = 1)
public abstract class DataBase extends RoomDatabase {
    public abstract TimeTableDao timeTableDao();
}
