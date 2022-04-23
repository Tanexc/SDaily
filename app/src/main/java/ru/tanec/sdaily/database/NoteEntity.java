package ru.tanec.sdaily.database;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Comparator;
import java.util.Date;

import ru.tanec.sdaily.custom.HTimePicker;
import ru.tanec.sdaily.custom.MTimePicker;


@Entity
public class NoteEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public long duration;
    public long beginDateMls;
    public long beginDayMls;
    public int startHour;
    public int startMinute;
    public int endHour;
    public int endMinute;
    public int type;
    public int dayOfWeek;
    public boolean finished;
    public boolean todoin;
    public boolean missed;
    public boolean notified;
    public boolean postNotified;
    public String title;
    public String description;
    public String time;
    public String date;

}
