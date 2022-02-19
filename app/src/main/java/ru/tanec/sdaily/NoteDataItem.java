package ru.tanec.sdaily;

import java.sql.Time;

class NoteDataItem extends ItemList {
    String title;
    String description;
    boolean finished;
    boolean todoin;
    int type;
    Time time;

    public NoteDataItem(String title, String description, boolean finished, boolean todoin, int type, Time time) {
        this.title = title;
        this.description = description;
        this.finished = finished;
        this.todoin = todoin;
        this.type = type;
        this.time = time;
    }
}
