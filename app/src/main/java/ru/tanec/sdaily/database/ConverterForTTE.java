package ru.tanec.sdaily.database;

import androidx.room.TypeConverter;

import ru.tanec.sdaily.adapters.items.RangeItem;

public class ConverterForTTE {
    @TypeConverter
    public String fromDialogItem(RangeItem[] timerange) {
        String data = "";
        int tl;
        try {
            tl = timerange.length;
        } catch (Exception e) {
            tl = 0;
        }
        for (int i = 0; i < tl; i++) {
            if (timerange[i].deleted != -1) {
                int id = timerange[i].id;
                int sh = timerange[i].start_hour;
                int sm = timerange[i].start_minute;
                int eh = timerange[i].end_hour;
                int em = timerange[i].end_minute;
                String title = timerange[i].title;
                data += id + ";" + sh + ";" + sm + ";" + eh + ";" + em + ";" + title + "]";
            }
        }
        if (!data.equals("")) {
            data = data.substring(0, data.length() - 1);
        }
        return data;
    }


    @TypeConverter
    public RangeItem[] toDialogItem(String dt) {
        RangeItem[] data;
        String[] stringData;
        try {
            stringData = dt.split("]");
        } catch (Exception e) {
            stringData = new String[0];
        }

        int sl;
        try {
            sl = stringData.length;
        } catch (Exception e) {
            sl = 0;
        }

        data = new RangeItem[sl];
        if (sl != 0) {
            for (int i = 0; i < sl; i++) {
                if (stringData[i].length() > 0) {
                    String[] timerange = stringData[i].split(";");
                    int id = Integer.parseInt(timerange[0]);
                    int sh = Integer.parseInt(timerange[1]);
                    int sm = Integer.parseInt(timerange[2]);
                    int eh = Integer.parseInt(timerange[3]);
                    int em = Integer.parseInt(timerange[4]);
                    String title = timerange[5];
                    data[i] = new RangeItem();
                    data[i].setStartTime(sh, sm);
                    data[i].setEndTime(eh, em);
                    data[i].setId(id);
                    data[i].setTitle(title);
                }
            }
        }
        return data;
    }
}
