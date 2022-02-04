package ru.tanec.sdaily;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MTimePicker extends androidx.appcompat.widget.AppCompatTextView {


    public MTimePicker(@NonNull Context context) {
        super(context);
    }

    public MTimePicker(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MTimePicker(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void plusMinute() {
        int minute = getMinute();
        if (minute == 55) {
            minute = 0;
        } else {
            minute += 5;
        }
        setMinute(minute);
    }

    public void minusMinute() {
        int minute = getMinute();
        if (minute == 0) {
            minute = 55;
        } else {
            minute -= 5;
        }
        setMinute(minute);
    }

    public void setMinute(Integer minute) {
        if (minute < 10) {
            this.setText((CharSequence) ("0" + minute));
        } else {
            this.setText((CharSequence) ("" + minute));
        }
    }

    public int getMinute() {
        return Integer.parseInt((String) this.getText());
    }
}
