package ru.tanec.sdaily;


import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
@TypeConverters({ConverterForTTE.class})
public class TimeTableEntity {

    @PrimaryKey
    public long id;
    public String title;
    @TypeConverters({ConverterForTTE.class})
    public DialogItem[] timerange;

}

