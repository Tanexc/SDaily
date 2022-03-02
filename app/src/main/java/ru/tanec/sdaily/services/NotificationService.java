package ru.tanec.sdaily.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.ContactsContract;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.tanec.sdaily.R;
import ru.tanec.sdaily.activity.MainActivity;
import ru.tanec.sdaily.adapters.TimeAdapter;
import ru.tanec.sdaily.adapters.items.NoteDataItem;
import ru.tanec.sdaily.adapters.items.RangeItem;
import ru.tanec.sdaily.adapters.items.TimeTableItem;
import ru.tanec.sdaily.database.DataBase;
import ru.tanec.sdaily.database.DataBaseApl;
import ru.tanec.sdaily.database.NoteDao;
import ru.tanec.sdaily.database.NoteEntity;
import ru.tanec.sdaily.database.TimeTableDao;
import ru.tanec.sdaily.database.TimeTableEntity;
import ru.tanec.sdaily.services.recivers.NotificationReceiver;


public class NotificationService extends Service {

    NotificationCompat.Builder mainNotification;
    DataBase db = DataBaseApl.getInstance().getDatabase();
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH-mm");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String[] daysOfWeek = new String[]{"", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Intent notificationIntent = new Intent(this, MainActivity.class);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            NotificationChannel ch = new NotificationChannel("101", "channel", NotificationManager.IMPORTANCE_HIGH);
            ch.enableVibration(true);
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).createNotificationChannel(ch);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mainNotification =
                new NotificationCompat.Builder(this, "101")
                        .setContentTitle("Notification")
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(false)
                        .setSmallIcon(R.drawable.calendar)
                        .setPriority(NotificationCompat.PRIORITY_MIN);

        startForeground(101, mainNotification.build());

        new Thread(() -> {
            while (true) {
                ArrayList<NoteEntity> notes = getDayNotes();
                for (NoteEntity note : notes) {
                    String[] timeNow = getTimeNow().split("-");
                    int d = Integer.parseInt(note.time.split("-")[1]) - Integer.parseInt(timeNow[1]);
                    if (note.time.split("-")[0].equals(timeNow[0]) && d < 5 && d > 0) {
                        String s;
                        try {
                            s = note.description.substring(0, 20) + "...";
                        } catch (Exception e) {
                            s = note.description.substring(0, 20);
                        }
                        sendNotification(note.title, s, note.id);
                    }
                }
            }
        }).start();

        new Thread(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("HH-mm");
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            while (true) {
                RangeItem[] ranges = getRanges();
                for (RangeItem range : ranges) {
                    if (range != null) {
                        Calendar calendar = Calendar.getInstance();
                        String[] timeNow = sdf.format(calendar.getTime()).split("-");
                        if (Integer.parseInt(timeNow[0]) == range.start_hour && (range.start_minute - Integer.parseInt(timeNow[1]) <= 5) && (range.start_minute - Integer.parseInt(timeNow[1]) >= 0)) {
                            long mls = range.getDuration();
                            String duration = range.getStringDuration();
                            startForeground(101, mainNotification.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)).setContentTitle(range.title + ". Remember that").setContentText(range.title + " " + duration).build());
                            vibrator.vibrate(VibrationEffect.createWaveform(new long[]{100, 200, 100, 200}, new int[]{VibrationEffect.EFFECT_TICK, 0, VibrationEffect.EFFECT_TICK, 0}, -1));
                            try {
                                Thread.sleep(mls);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }).start();

        return START_NOT_STICKY;
    }

    void sendNotification(String title, String text, long id) {
        Intent okIntent = new Intent(this, NotificationReceiver.class);
        okIntent.putExtra("action", 1);
        okIntent.putExtra("notification", id);
        Intent dismissIntent = new Intent(this, NotificationReceiver.class);
        dismissIntent.putExtra("action", 2);
        dismissIntent.putExtra("notification", id);

        PendingIntent okPendingIntent = PendingIntent.getBroadcast(this, 0, okIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(this, 0, dismissIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, "101")
                        .setContentTitle(title)
                        .setSmallIcon(R.drawable.new_moon)
                        .setContentText(text)
                        .addAction(R.drawable.ic_baseline_nights_stay_24, "Ok", okPendingIntent)
                        .addAction(R.drawable.ic_baseline_wb_sunny_24, "Dismiss", dismissPendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);
        Notification notification = builder.build();

        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(new long[]{200, 600, 200, 600}, -1);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, notification);

    }

    public ArrayList<NoteEntity> getDayNotes() {

        String date = getDateNow();

        NoteDao noteDao = db.noteDao();
        LiveData<List<NoteEntity>> data = noteDao.getAll();
        List<NoteEntity> notes = data.getValue();

        ArrayList<NoteEntity> dayNotes = new ArrayList<NoteEntity>();

        assert notes != null;
        for (NoteEntity note : notes) {
            if (note.date.equals(date)) {
                dayNotes.add(note);
            }
        }
        return dayNotes;
    }

    public RangeItem[] getRanges() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        if (day < 0) {
            day = 6;
        }
        TimeTableDao td = db.timeTableDao();
        TimeTableEntity deal = td.getById(day);
        RangeItem[] d = deal.timerange;
        return d;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public String getTimeNow() {
        Calendar calendar = Calendar.getInstance();
        return timeFormat.format(calendar.getTime());
    }

    public String getDateNow() {
        Calendar calendar = Calendar.getInstance();
        return dateFormat.format(calendar.getTime());

    }
}
