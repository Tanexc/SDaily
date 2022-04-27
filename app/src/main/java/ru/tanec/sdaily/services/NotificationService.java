package ru.tanec.sdaily.services;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DiffUtil;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ru.tanec.sdaily.R;
import ru.tanec.sdaily.activity.MainActivity;
import ru.tanec.sdaily.adapters.DialogAdapter;
import ru.tanec.sdaily.adapters.TimeAdapter;
import ru.tanec.sdaily.adapters.items.NoteDataItem;
import ru.tanec.sdaily.adapters.items.RangeItem;
import ru.tanec.sdaily.adapters.items.TimeTableItem;
import ru.tanec.sdaily.custom.StaticValues;
import ru.tanec.sdaily.database.DataBase;
import ru.tanec.sdaily.database.DataBaseApl;
import ru.tanec.sdaily.database.NoteDao;
import ru.tanec.sdaily.database.NoteEntity;
import ru.tanec.sdaily.database.TimeTableDao;
import ru.tanec.sdaily.database.TimeTableEntity;
import ru.tanec.sdaily.helpers.NoteDiffUtil;
import ru.tanec.sdaily.services.recivers.NotificationReceiver;


public class NotificationService extends LifecycleService {
    int cnt = 0;
    NotificationCompat.Builder mainNotification;
    DataBase db = DataBaseApl.instance.getDatabase();
    List<NoteEntity> notes;
    Thread notesThread = new Thread();

    SimpleDateFormat timeFormat = new SimpleDateFormat("HH-mm");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    String[] daysOfWeek = new String[]{"", "Якшәмбе", "Дүшәмбе", "Сишәмбе", "Чәршәмбе", "Пәнҗешәмбе", "Җомга", "Шимбә"};

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        Intent notificationIntent = new Intent(this, MainActivity.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel ch = new NotificationChannel("101", "channel", NotificationManager.IMPORTANCE_HIGH);
            ch.enableVibration(true);
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).createNotificationChannel(ch);
            NotificationChannel ch2 = new NotificationChannel("102", "channel2", NotificationManager.IMPORTANCE_HIGH);
            ch2.enableVibration(true);
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).createNotificationChannel(ch2);
            NotificationChannel ch3 = new NotificationChannel("103", "channel3", NotificationManager.IMPORTANCE_HIGH);
            ch3.enableVibration(true);
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).createNotificationChannel(ch3);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mainNotification =
                new NotificationCompat.Builder(this, "101")
                        .setContentTitle("Smart Daily")
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(false)
                        .setSmallIcon(R.mipmap.icon5)
                        .setPriority(NotificationCompat.PRIORITY_MIN);

        startForeground(101, mainNotification.build());

        LiveData<List<NoteEntity>> nt = db.noteDao().getLiveByDate(StaticValues.getDayMls());
        nt.observe(this, noteEntities -> {
            new Thread(() -> {
                Collections.sort(noteEntities, new Comparator<NoteEntity>() {
                    @Override
                    public int compare(NoteEntity noteEntity, NoteEntity t1) {
                        return Long.compare(noteEntity.beginDateMls, t1.beginDateMls);
                    }
                });

                for (NoteEntity note : noteEntities) {
                    if (!note.notified) {
                        if (note.beginDateMls > Calendar.getInstance().getTime().getTime()) {
                            try {
                                Thread.sleep(note.beginDateMls - Calendar.getInstance().getTime().getTime());
                                sendNotification(note.title, note.description, note.id, 0, note.type, "" + note.startHour + "-" + note.startMinute);
                                note.notified = true;
                                db.noteDao().update(note);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    } else if (!note.postNotified) {
                        try {
                            Thread.sleep(note.duration);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        sendNotification(note.title, note.description, note.id, 1, note.type, "" + note.startHour + "-" + note.startMinute);
                        note.postNotified = true;
                        db.noteDao().update(note);
                    }
                }
            }).start();

        });


        return START_NOT_STICKY;
    }

    void sendNotification(String title, String text, long id, Integer notificationFun, @Nullable Integer type, @Nullable String startTime) {

        int ic;
        if (type == 0) {
            ic = R.drawable.type0;
        } else if (type == 1) {
            ic = R.drawable.type1;
        } else {
            ic = R.drawable.type2;
        }


        if (startTime != null) {
            text = "At " + startTime + ". " + text;
        }
        NotificationCompat.Builder builder;

        // fun
        // 0 - send first notification
        // other - send post notification

        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra("notify", "activity");

        PendingIntent ntfIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Intent okIntent = new Intent(this, NotificationReceiver.class);
        okIntent.putExtra("notify", "ok");
        okIntent.putExtra("note", id);

        Intent dismissIntent = new Intent(this, NotificationReceiver.class);
        dismissIntent.putExtra("notify", "dismiss");
        dismissIntent.putExtra("note", id);


        Intent yesIntent = new Intent(this, NotificationReceiver.class);
        yesIntent.putExtra("notify", "yes");
        yesIntent.putExtra("note", id);


        Intent noIntent = new Intent(this, NotificationReceiver.class);
        noIntent.putExtra("notify", "no");
        noIntent.putExtra("note", id);


        PendingIntent okPendingIntent = PendingIntent.getBroadcast(this, 0, okIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(this, 0, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent yesPendingIntent = PendingIntent.getBroadcast(this, 0, yesIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent noPendingIntent = PendingIntent.getBroadcast(this, 0, noIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        if (notificationFun == 0) {
            builder =
                    new NotificationCompat.Builder(this, "102")
                            .setContentTitle(title)
                            .setSmallIcon(ic)
                            .setContentText(text)
                            .setContentIntent(ntfIntent)
                            .addAction(R.drawable.ic_baseline_nights_stay_24, "Ok", okPendingIntent)
                            .addAction(R.drawable.ic_baseline_wb_sunny_24, "Dismiss", dismissPendingIntent)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setAutoCancel(true);
        } else {
            builder =
                    new NotificationCompat.Builder(this, "103")
                            .setContentTitle(title)
                            .setSmallIcon(ic)
                            .setContentText(text)
                            .setContentIntent(ntfIntent)
                            .addAction(R.drawable.ic_baseline_nights_stay_24, "Yes", yesPendingIntent)
                            .addAction(R.drawable.ic_baseline_wb_sunny_24, "No", noPendingIntent)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setAutoCancel(true);
        }
        Notification notification = builder.build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify((int) id, notification);

    }

    public RangeItem[] getRanges() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        if (day < 0) {
            day = 6;
        }
        TimeTableDao td = db.timeTableDao();
        TimeTableEntity deal = td.getById(day);
        RangeItem[] ranges = new RangeItem[]{};

        if (deal != null) {
            ranges = deal.timerange;
        }
        return ranges;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void lifeDataNotes() {
        NoteDao nd = db.noteDao();
        LiveData<List<NoteEntity>> ld = nd.getAll();
        notes = new ArrayList<>();
        ld.observe(this, noteEntities -> {
            if (notes.size() != noteEntities.size() | noteEntities.size() != 0) {
                notesThread.interrupt();
                cnt += 1;
                notes = noteEntities;
                notes.sort((Comparator<NoteEntity>) (noteEntity, t1) -> Long.compare(noteEntity.beginDateMls, t1.beginDateMls));
                notesThread = new Thread(() -> {

                    for (NoteEntity note : notes) {
                        note.title = "" + cnt;
                        long t = Calendar.getInstance().getTime().getTime();
                        if (note.beginDateMls - t > 0) {
                            try {
                                Thread.sleep(note.beginDateMls - t);
                                sendNotification(note.title, note.description, note.id, 0, note.type, null);
                                note.notified = true;
                                nd.update(note);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        } else {
                            if (!note.postNotified && note.beginDateMls + note.duration < t) {
                                sendNotification(note.title, "Выполнили ли вы задачу? Если нет, то мы ее перенесем.", note.id, 1, note.type, null);
                                note.postNotified = true;
                                nd.update(note);
                            }

                        }

                    }
                });
                notesThread.start();
            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return null;
    }

}
