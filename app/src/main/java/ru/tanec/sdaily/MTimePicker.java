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
        int minute = Integer.parseInt((String) this.getText());
        if (minute == 55) {
            minute = 0;
        } else {
            minute += 5;
        }
        this.setText((CharSequence) ("" + minute));
    }

    public void minusMinute() {
        int minute = Integer.parseInt((String) this.getText());
        if (minute == 0) {
            minute = 55;
        } else {
            minute -= 5;
        }
        this.setText((CharSequence) ("" + minute));
    }

    public void setMinute(Integer minute) {
        this.setText((CharSequence) ("" + minute));
    }
}
