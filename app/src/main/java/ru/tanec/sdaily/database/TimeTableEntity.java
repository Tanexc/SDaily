package ru.tanec.sdaily.database;


import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import ru.tanec.sdaily.adapters.items.RangeItem;

@Entity
@TypeConverters({ConverterForTTE.class})
public class TimeTableEntity {

    @PrimaryKey
    public long id;
    public String title;
    @TypeConverters({ConverterForTTE.class})
    public RangeItem[] timerange;

}

