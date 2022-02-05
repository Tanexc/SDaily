package ru.tanec.sdaily;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {TimeTableEntity.class}, version = 1)
public abstract class DataBase extends RoomDatabase {
    public abstract TimeTableDao timeTableDao();
}
