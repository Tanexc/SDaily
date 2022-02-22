package ru.tanec.sdaily.services.recivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        switch(intent.getIntExtra("action", 0)){
            case 5:
                Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(200);
                Toast.makeText(context, "fdbdfb", Toast.LENGTH_SHORT).show();
                context.sendBroadcast(new Intent(Intent.EXTRA_TEXT));
        }
    }
}
