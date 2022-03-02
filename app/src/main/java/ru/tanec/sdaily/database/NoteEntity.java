package ru.tanec.sdaily.database;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;


@Entity
public class NoteEntity {

    @PrimaryKey
    public long id;
    public String title;
    public long duration;
    public String description;
    public boolean finished;
    public boolean todoin;
    public int type;
    public boolean missed;
    public String date;
    public String time;
    public boolean notified;
    public boolean postNotified;
    public long beginDateMls;
    public int dayOfWeek;
    public int startHour;
    public int startMinute;
    public int endHour;
    public int endMinute;

}
