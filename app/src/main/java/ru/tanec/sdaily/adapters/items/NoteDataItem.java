package ru.tanec.sdaily.adapters.items;

import android.service.autofill.DateTransformation;
import android.service.autofill.DateValueSanitizer;
import android.text.format.DateFormat;
import android.text.format.DateUtils;

import com.google.gson.internal.bind.DateTypeAdapter;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.Date;

import ru.tanec.sdaily.database.NoteEntity;

public class NoteDataItem extends ItemList {
    public String title;
    public String description;
    public String time;
    public String date;
    public long id;
    public long duration;
    public long beginDateMls;
    public int startHour;
    public int startMinute;
    public int endHour;
    public int endMinute;
    public int type;
    public int dayOfWeek;
    public boolean finished;
    public boolean todoin;
    public boolean missed = false;
    public boolean notified = false;
    public boolean postNotified = false;

    public NoteDataItem(){}

    public void setFromEntity(NoteEntity entity) {
        id = entity.id;
        title = entity.title;
        description = entity.description;
        finished = entity.finished;
        todoin = entity.todoin;
        type = entity.type;
        date = entity.date;
        duration = entity.duration;
        dayOfWeek = entity.dayOfWeek;
        time = entity.time;
        startHour = entity.startHour;
        startMinute = entity.startMinute;
        endHour = entity.endHour;
        endMinute = entity.endMinute;
        notified = entity.notified;
        postNotified = entity.postNotified;
    }

    public void setTimeBoarders(int startHour, int startMinute, int endHour, int endMinute) {
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
    }

    public void setTime() {
        String sh = ((Integer) startHour).toString();
        String sm = ((Integer) startMinute).toString();
        if (sh.length() < 2) {
            sh = "0" + sh;
        }
        if (sm.length() < 2) {
            sm = "0" + sh;
        }
        this.time = sh + "-" + sm;

    }

    public long getDuration() {
        int d_hour = endHour - startHour;
        int d_minute = endMinute - startMinute;
        if (d_minute < 0) {
            d_minute = 60 + d_minute;
            d_hour -= 1;
        }
        return d_hour * 3600000L + d_minute * 60000L;
    }

    public NoteEntity getEntity() {
        NoteEntity entity = new NoteEntity();
        entity.duration = duration;
        entity.type = type;
        entity.date = date;
        entity.endHour = endHour;
        entity.startHour = startHour;
        entity.startMinute = startMinute;
        entity.endMinute = endMinute;
        entity.beginDateMls = beginDateMls;
        entity.finished = finished;
        entity.description = description;
        entity.id = id;
        entity.dayOfWeek = dayOfWeek;
        entity.title = title;
        entity.time = time;
        entity.notified = notified;
        entity.postNotified = postNotified;
        return entity;
    }

    public String getDescriptionSmall() {
        if (description.length() > 20) {
            return description.substring(0, 20);
        } else {
            return description;
        }
    }
    public String getTime(){
        return time;
    }
}
