package ru.tanec.sdaily;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

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
