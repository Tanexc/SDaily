package ru.tanec.sdaily.database;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import ru.tanec.sdaily.custom.HTimePicker;
import ru.tanec.sdaily.custom.MTimePicker;


@Entity
public class NoteEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;
    public String title;   //1
    public long duration;  //1 // разность начала и конца в милисекундах
    public String description;//1
    public boolean finished;//1
    public boolean todoin;//1
    public int type;//1
    public boolean missed; //1     // по дефолту false
    public String date; //1 дата начала в формате yyyy-MM-dd
    public String time; //1  время начала в формате HH-mm
    public boolean notified; //1    // по дефолту false
    public boolean postNotified; //1     // по дефолту false
    public long beginDateMls; //1 полная дата начала задачи (включая час минуту и секунду)
    public long beginDayMls; //1 дата начала ( у этого поля час, минуты и секунды должны быть равны нулю )
    public int dayOfWeek; // день недели можно получить из объекта calendar
    public int startHour;//1
    public int startMinute;//1
    public int endHour;
    public int endMinute;

//    public NoteEntity(String title, String description, int type, boolean missed, boolean notified, boolean postNotified, HTimePicker startHour, MTimePicker startMinute, boolean todoin) {
//        this.title = title;
//        this.description = description;
//        this.type = type;
//        this.missed = missed;
//        this.notified = notified;
//        this.postNotified = postNotified;
//        this.startHour = startHour.getHour();
//        this.startMinute = startMinute.getMinute();
//        this.todoin = todoin;
//    }

//    public NoteEntity() {}
}
