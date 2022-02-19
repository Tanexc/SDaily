package ru.tanec.sdaily;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class NotificationService extends Service {

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

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, "101")
                        .setContentTitle("Notification")
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(false)
                        .setSmallIcon(R.drawable.ic_baseline_nights_stay_24)
                        .setPriority(NotificationCompat.PRIORITY_MIN);

        startForeground(101, builder.build());

        new Thread(() -> {
            int cnt = 0;
            while (true) {
                try {
                    Thread.sleep(600000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String[] note =  getDayNote();
                sendNotification(note[0], note[1]);

            }
        }).start();

        return START_NOT_STICKY;
    }

    void sendNotification(String title, String text) {
        Intent notificationIntent = new Intent(this, NotificationReceiver.class);
        notificationIntent.putExtra("action", 5);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, "101")
                        .setContentTitle(title)
                        .setSmallIcon(R.drawable.new_moon)
                        .setContentText(text)
                        .addAction(R.drawable.ic_baseline_nights_stay_24, "qwe", pendingIntent)
                        .setVibrate(new long[]{2000, 2000})
                        .setPriority(NotificationCompat.PRIORITY_HIGH);
        Notification notification = builder.build();

        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(new long[]{200, 600, 200, 600}, -1);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, notification);

    }

    public String[] getDayNote() {
        String[] note  = new String[]{};
        return note;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
