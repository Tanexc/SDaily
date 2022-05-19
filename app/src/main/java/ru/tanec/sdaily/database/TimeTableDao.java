package ru.tanec.sdaily.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface TimeTableDao {

    @Query("SELECT * FROM timetableentity")
    LiveData<List<TimeTableEntity>> getAll();
    @Query("SELECT * FROM timetableentity WHERE id = :id")
    TimeTableEntity getById(long id);

    @Query("SELECT * FROM timetableentity WHERE title = :title")
    TimeTableEntity getByTitle(String title);



    @Insert
    void insert(TimeTableEntity tdi);

    @Update
    void update(TimeTableEntity tdi);

    @Delete
    void delete(TimeTableEntity tdi);

}
