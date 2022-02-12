package ru.tanec.sdaily;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TimeTableDao {

    @Query("SELECT * FROM timetableentity")
    List<TimeTableEntity> getAll();
    @Query("SELECT * FROM timetableentity WHERE id = :id")
    TimeTableEntity getById(long id);

    @Insert
    void insert(TimeTableEntity employee);

    @Update
    void update(TimeTableEntity employee);

    @Delete
    void delete(TimeTableEntity employee);

}
