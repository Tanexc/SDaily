package ru.tanec.sdaily;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class TimeTableEntity {

    @PrimaryKey
    public long id;
    public String title;
    public DialogItem[] timerange;


}
