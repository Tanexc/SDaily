package ru.tanec.sdaily;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HTimePicker extends androidx.appcompat.widget.AppCompatTextView {
    public HTimePicker(@NonNull Context context) {
        super(context);
    }

    public HTimePicker(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HTimePicker(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void plusHour() {
        int hour = getHour();
        if (hour == 23) {
            hour = 0;
        } else {
            hour += 1;
        }
        setHour(hour);
    }

    public void minusHour() {
        int hour = getHour();
        if (hour == 0) {
            hour = 23;
        } else {
            hour -= 1;
        }
        setHour(hour);
    }

    public void setHour(int hour) {
        if (hour < 10) {
            this.setText((CharSequence) ("0" + hour));
        } else {
            this.setText((CharSequence) ("" + hour));
        }
    }

    public int getHour() {
        return Integer.parseInt((String) this.getText());
    }

}
