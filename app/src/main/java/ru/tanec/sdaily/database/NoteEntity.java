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
    public long duration; // разность начала и конца в милисекундах
    public String description;
    public boolean finished;
    public boolean todoin;
    public int type;
    public boolean missed; // по дефолту false
    public String date; // дата начала в формате yyyy-MM-dd
    public String time; // время начала в формате HH-mm
    public boolean notified; // по дефолту false
    public boolean postNotified; // по дефолту false
    public long beginDateMls; // полная дата начала задачи (включая час минуту и секунду)
    public long beginDayMls; // дата начала ( у этого поля час, минуты и секунды должны быть равны нулю )
    public int dayOfWeek; // день недели можно получить из объекта calendar
    public int startHour;
    public int startMinute;
    public int endHour;
    public int endMinute;

}
