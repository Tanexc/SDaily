package ru.tanec.sdaily;

import androidx.room.TypeConverter;

public class ConverterForTTE {
    @TypeConverter
    public String fromDialogItem(DialogItem[] timerange) {
        String data = "";
        int tl;
        try {
            tl = timerange.length;
        } catch (Exception e) {
            tl = 0;
        }
        for (int i = 0; i < tl; i++) {
            int id = timerange[i].id;
            int sh = timerange[i].start_hour;
            int sm = timerange[i].start_minute;
            int eh = timerange[i].end_hour;
            int em = timerange[i].end_minute;
            String title = timerange[i].title;
            data += id + ";" + sh + ";" + sm + ";" + eh + ";" + em + ";" + title + "]";
        }
        if (!data.equals("")) {
            data = data.substring(0, data.length() - 1);
        }
        return data;
    }


    @TypeConverter
    public DialogItem[] toDialogItem(String dt) {
        DialogItem[] data;
        String[] stringData = dt.split("]");
       int sl;
        try {
            sl = stringData.length;
        } catch (Exception e) {
            sl = 0;
        }

        data = new DialogItem[sl];
        if (sl != 1) {
            for (int i = 0; i < sl; i++) {
                String[] timerange = stringData[i].split(";");
                int id = Integer.parseInt(timerange[0]);
                int sh = Integer.parseInt(timerange[1]);
                int sm = Integer.parseInt(timerange[2]);
                int eh = Integer.parseInt(timerange[3]);
                int em = Integer.parseInt(timerange[4]);
                String title = timerange[5];
                data[i] = new DialogItem();
                data[i].setStartTime(sh, sm);
                data[i].setEndTime(eh, em);
                data[i].setId(id);
                data[i].setTitle(title);
            }
        }
        return data;
    }
}
