package ru.tanec.sdaily.services.recivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Range;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import ru.tanec.sdaily.adapters.items.NoteDataItem;
import ru.tanec.sdaily.adapters.items.RangeItem;
import ru.tanec.sdaily.database.DataBase;
import ru.tanec.sdaily.database.DataBaseApl;
import ru.tanec.sdaily.database.NoteDao;
import ru.tanec.sdaily.database.NoteEntity;
import ru.tanec.sdaily.database.TimeTableDao;
import ru.tanec.sdaily.database.TimeTableEntity;

public class NotificationReceiver extends BroadcastReceiver {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void onReceive(Context context, Intent intent) {
        switch(intent.getIntExtra("action", 0)){
            case 1:

            case 2:
                noteReplace(intent.getIntExtra("notification", 0));
        }

    }

    public void noteReplace(long id) {
        DataBase db = DataBaseApl.getInstance().getDatabase();
        NoteDao nd = db.noteDao();

        TimeTableDao td = db.timeTableDao();

        NoteDataItem newNote = new NoteDataItem();

        NoteEntity noteToReplase = nd.getById(id);

        newNote.setFromEntity(noteToReplase);

        nd.delete(noteToReplase);

        NoteEntity[] notesLow = nd.getByType(0);
        NoteEntity[] notesMedium = nd.getByType(1);
        NoteEntity[] notesHigh = nd.getByType(2);

        List<TimeTableEntity> timeTable = td.getAll().getValue();

        int nH = Integer.parseInt(newNote.time.split("-")[0]);
        String nM = newNote.time.split("-")[1];
        String[] date = newNote.date.split("-");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
        float duration = (float) newNote.duration / 3600000;


        switch (newNote.type) {
            case 0:
                while (true) {
                    String dt = dateFormat.format(calendar.getTime());
                    int day = calendar.get(Calendar.DAY_OF_WEEK) - 2;
                    if (day < 0) {
                        day = 6;
                    }
                    Boolean[] dayfill = getDayFill(td.getById(day).timerange);
                    for (NoteEntity n: notesHigh) {
                        if (n.date.equals(dt)) {
                            if (0 < n.startMinute && n.startMinute < 40) {
                                dayfill[n.startHour] = null;
                            }
                            for (int i = n.startHour + 1; i < n.endHour; i++) {
                                dayfill[i] = true;
                            }
                            if (20 >= n.endMinute) {
                                dayfill[n.endHour] = null;
                            }
                        }
                    }
                    for (NoteEntity n: notesMedium) {
                        if (n.date.equals(dt)) {
                            if (0 < n.startMinute && n.startMinute < 40) {
                                dayfill[n.startHour] = null;
                            }
                            for (int i = n.startHour + 1; i < n.endHour; i++) {
                                dayfill[i] = true;
                            }
                            if (20 >= n.endMinute) {
                                dayfill[n.endHour] = null;
                            }
                        }
                    }
                    for (NoteEntity n: notesLow) {
                        if (n.date.equals(dt)) {
                            if (0 < n.startMinute && n.startMinute < 40) {
                                dayfill[n.startHour] = null;
                            }
                            for (int i = n.startHour + 1; i < n.endHour; i++) {
                                dayfill[i] = true;
                            }
                            if (20 >= n.endMinute) {
                                dayfill[n.endHour] = null;
                            }
                        }
                    }
                    int h = 0;
                    while (h < 23) {
                        float dr = 0;
                        int d = 0;
                        while (!dayfill[h + d]) {
                            d += 1;
                            if (dayfill[h + d] == null) {
                                if (dr == 0) {
                                    dr += 0.33;
                                } else {
                                    dr += 0.67;
                                }
                            } else {
                                dr += 1;
                            }
                            if (dr >= duration) {
                                newNote.startHour = h;
                                if (dayfill[h] == null) {
                                    newNote.startMinute = 40;
                                }
                                newNote.endHour = h + d;
                                if (dayfill[h + d] == null) {
                                    newNote.endHour -= 1;
                                    newNote.endMinute = 20;
                                }
                                newNote.duration = (long) dr * 3600000;
                                h = 100;
                                break;
                            }
                        }
                        h += d + 1;
                    }
                    if (h == 100) {
                        break;
                    }
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                }
                newNote.setTime();
                break;
            case 1:
                break;
            case 2:
                break;
        }
    }

    public Boolean[] getDayFill(RangeItem[] ranges) {
        Boolean[] fill = new Boolean[24];
        for (RangeItem rangeItem : ranges) {
            if (rangeItem != null) {
                int start = rangeItem.getStartTime()[0];
                int end = rangeItem.getEndTime()[0];
                int end_minute = rangeItem.getEndTime()[1];
                if (end_minute > 40) {
                    end += 1;
                } else if (20 < end_minute & end_minute < 40) {
                    fill[end] = null;
                }
                for (int k = start; k < end; k++) {
                    fill[k] = true;
                }
            }
        }
        return fill;
    }
}
