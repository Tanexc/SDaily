package ru.tanec.sdaily;

import java.sql.Time;

public class NoteDataItem extends ItemList {
    int id;
    String title;
    String description;
    boolean finished;
    boolean todoin;
    int type;
    Time time;
}
