package ru.tanec.sdaily.adapters.items;

import ru.tanec.sdaily.database.NoteEntity;

public class NoteDataItem extends ItemList {
    public String title;
    public String description;
    public boolean finished;
    public boolean todoin;
    public long duration;
    public long beginDateMls;
    public int type;
    public String date;
    public int dayOfWeek;
    public String time;
    public int startHour;
    public int startMinute;
    public int endHour;
    public int endMinute;

    public NoteDataItem(String title, String description, boolean finished, boolean todoin, int type, String date, long duration, int dayOfWeek, String time) {
        this.title = title;
        this.description = description;
        this.finished = finished;
        this.todoin = todoin;
        this.type = type;
        this.date = date;
        this.duration = duration;
        this.dayOfWeek = dayOfWeek;
        this.time = time;
    }

    public NoteDataItem() {

    }

    public void setFromEntity(NoteEntity entity) {
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
        return entity;
    }
}
