package ru.tanec.sdaily.fragments;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import ru.tanec.sdaily.R;
import ru.tanec.sdaily.custom.TimePick;

public class TimePickDialog extends DialogFragment {
    int titleRes;
    int hour;
    int minute;
    TimePick timePick;
    TextView title;
    TextView timeView;
    Button saveButton;
    Button cancelButton;


    public TimePickDialog(int titleResource, int hour, int minute, TextView timeView) {
        super(R.layout.dialog_time_pick);
        this.titleRes = titleResource;
        this.hour = hour;
        this.minute = minute;
        this.timeView = timeView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = this.getView().findViewById(R.id.title);
        timePick = this.getView().findViewById(R.id.time_pick);
        saveButton = this.getView().findViewById(R.id.save);
        cancelButton = this.getView().findViewById(R.id.cancel);

        saveButton.setOnClickListener(l -> {
            timeView.setText(timePick.getStringTime());
            hour = timePick.getTime()[0];
            minute = timePick.getTime()[1];
            onDestroy();
            onStop();
        });

        cancelButton.setOnClickListener(l -> {
            onDestroy();
            onStop();
        });
        timePick.setTime(hour, minute);
        title.setText(titleRes);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;
    }
}
