package ru.tanec.sdaily.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ru.tanec.sdaily.R;

public class TimePick extends LinearLayout {
    TextView hour;
    TextView minute;
    ImageView plusHour;
    ImageView plusMinute;
    ImageView minusHour;
    ImageView minusMinute;

    int HOUR = 0;
    int MINUTE = 0;

    public TimePick(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.time_pick, this);

        hour = findViewById(R.id.hour);
        minute = findViewById(R.id.minute);
        plusHour = findViewById(R.id.plus_hour);
        plusMinute = findViewById(R.id.plus_minute);
        minusHour = findViewById(R.id.minus_hour);
        minusMinute = findViewById(R.id.minus_minute);
        update();
        setListeners();
    }

    public TimePick(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.time_pick, this);

        hour = findViewById(R.id.hour);
        minute = findViewById(R.id.minute);
        plusHour = findViewById(R.id.plus_hour);
        plusMinute = findViewById(R.id.plus_minute);
        minusHour = findViewById(R.id.minus_hour);
        minusMinute = findViewById(R.id.minus_minute);
        update();
        setListeners();

    }

    private void upHour() {
        HOUR += 1;
        if (HOUR >= 24) {
            HOUR = 0;
        }
    }

    private void upMinute() {
        MINUTE += 5;
        if (MINUTE >= 60) {
            MINUTE = 0;
        }
    }

    private void downHour() {
        HOUR -= 1;
        if (HOUR == -1) {
            HOUR = 23;
        }
    }

    private void downMinute() {
        MINUTE -= 5;
        if (MINUTE < 0) {
            MINUTE = 55;
        }
    }

    private void update() {
        String textHour = "" + HOUR;
        String textMinute = "" + MINUTE;
        if (HOUR < 10) {
            textHour = "0" + HOUR;
        }
        if (MINUTE < 10) {
            textMinute = "0" + MINUTE;
        }

        hour.setText(textHour);
        minute.setText(textMinute);
    }

    private void setListeners() {
        plusHour.setOnClickListener(l -> {
            upHour();
            update();
        });
        plusMinute.setOnClickListener(l -> {
            upMinute();
            update();
        });
        minusHour.setOnClickListener(l -> {
            downHour();
            update();
        });
        minusMinute.setOnClickListener(l -> {
            downMinute();
            update();
        });
    }

    public Integer[] getTime() {
        return new Integer[]{HOUR, MINUTE};
    }

    public String getStringTime() {
        return "" + hour.getText() + ":" + minute.getText();
    }

    public void setTime(int hour, int minute) {
        HOUR = hour;
        MINUTE = minute;
        update();
    }

}
