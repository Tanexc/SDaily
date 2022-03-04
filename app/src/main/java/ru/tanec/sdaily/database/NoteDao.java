package ru.tanec.sdaily.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface NoteDao {

    @Query("SELECT * FROM noteentity")
    LiveData<List<NoteEntity>> getAll();

    @Query("SELECT * FROM noteentity")
    NoteEntity[] getAllNotes();

    @Query("SELECT * FROM noteentity WHERE id = :id")
    NoteEntity getById(long id);

    @Query("SELECT * FROM noteentity WHERE type = :type")
    NoteEntity[] getByType(int type);

    @Query("SELECT * FROM noteentity WHERE beginDayMls = :date")
    NoteEntity[] getByDate(long date);

    @Query("SELECT * FROM noteentity WHERE beginDayMls = :date")
    LiveData<List<NoteEntity>> getLiveByDate(long date);


    @Insert
    void insert(NoteEntity note);

    @Update
    void update(NoteEntity note);

    @Delete
    void delete(NoteEntity note);

}
