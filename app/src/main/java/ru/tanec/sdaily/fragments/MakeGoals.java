package ru.tanec.sdaily.fragments;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import ru.tanec.sdaily.R;
import ru.tanec.sdaily.custom.CollapsibleCalendar;
import ru.tanec.sdaily.custom.HTimePicker;
import ru.tanec.sdaily.custom.MTimePicker;
import ru.tanec.sdaily.custom.StaticValues;
import ru.tanec.sdaily.database.DataBase;
import ru.tanec.sdaily.database.DataBaseApl;
import ru.tanec.sdaily.database.NoteDao;
import ru.tanec.sdaily.database.NoteEntity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MakeGoals extends DialogFragment {

    public Button apply;
    public Button close;
    private TextView title;
    private TextInputEditText description;
    private boolean finished;
    private boolean missed;
    private int type;
    private long id;
    private boolean notified;
    private boolean postNotified;
    private boolean todoin;
    private HTimePicker startHour;
    private MTimePicker startMinute;
    private HTimePicker startHourD;
    private MTimePicker startMinuteD;
    private String time;

    private DataBase db = DataBaseApl.instance.getDatabase();
    private NoteDao nd;

    long duration;
    long beginDateMls;
    long beginDayMls;

    NoteEntity newNote;

    public Button btnplsthour;
    public Button btnplsminute;
    public Button btnminusminute;
    public Button btnminushour;
    CollapsibleCalendar calendar;
    public Button plusDurationhours;
    public Button minusDurationhours;
    public Button minusDurationminuts;
    public Button plusDurationminuts;
    //    public RadioButton dotodoin;
//    public RadioButton vtodoin;
    public ImageButton redTypeBtn;
    public ImageButton greenTypeBtn;
    public ImageButton yellowTypeBtn;
    public ImageButton darkRedButton;
    public ImageButton darkYellowButton;
    public ImageButton darkGreenButton;


    private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH-mm");

    public MakeGoals() {
        super(R.layout.fragment_makegoal);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        redTypeBtn = view.findViewById(R.id.red);
        greenTypeBtn = view.findViewById(R.id.green);
        yellowTypeBtn = view.findViewById(R.id.yellow);
        darkRedButton = view.findViewById(R.id.circle_red);
        darkYellowButton = view.findViewById(R.id.yellow_dark);
        darkGreenButton = view.findViewById(R.id.green_dark);

        redTypeBtn.setOnClickListener(v -> {
            type = 2;
            darkYellowButton.setVisibility(View.GONE);
            darkRedButton.setVisibility(View.VISIBLE);
            darkGreenButton.setVisibility(View.GONE);
        });


        yellowTypeBtn.setOnClickListener(view1 -> {
            type = 1;
            darkYellowButton.setVisibility(View.VISIBLE);
            darkGreenButton.setVisibility(View.GONE);
            darkRedButton.setVisibility(View.GONE);
        });

        greenTypeBtn.setOnClickListener(v -> {
            type = 0;
            darkYellowButton.setVisibility(View.GONE);
            darkRedButton.setVisibility(View.GONE);
            darkGreenButton.setVisibility(View.VISIBLE);
        });

//        dotodoin= view.findViewById(R.id.todoin_do);
//        dotodoin.setOnClickListener(view1 -> {
//            vtodoin.setChecked(false);
//            todoin = vtodoin.isChecked();
//        });
//
//        vtodoin= view.findViewById(R.id.todoin_v);
//        vtodoin.setOnClickListener(view1 -> dotodoin.setChecked(false));

        calendar = view.findViewById(R.id.calendar);

        apply = view.findViewById(R.id.apply);

        apply.setOnClickListener(view1 -> {
            saveNote();
            dismiss();
        });

        close = view.findViewById(R.id.close);
        close.setOnClickListener(view1 -> {
            dismiss();
        });
        title = view.findViewById(R.id.title);
        description = view.findViewById(R.id.description);
        missed = false;
        notified = false;
        postNotified = false;
        finished = false;

        startHour = view.findViewById(R.id.start_hour);
        startMinute = view.findViewById(R.id.start_minute);

        startHourD = view.findViewById(R.id.hour_d);
        startMinuteD = view.findViewById(R.id.minute_d);

        btnplsminute = view.findViewById(R.id.plus_s_minute);
        btnplsminute.setOnClickListener(view1 -> startMinute.plusMinute());

        btnplsthour = view.findViewById(R.id.plus_s_hour);
        btnplsthour.setOnClickListener(view1 -> startHour.plusHour());

        btnminusminute = view.findViewById(R.id.minus_s_minute);
        btnminusminute.setOnClickListener(view1 -> startMinute.minusMinute());

        btnminushour = view.findViewById(R.id.minus_s_hour);
        btnminushour.setOnClickListener(view1 -> startHour.minusHour());

        plusDurationhours = view.findViewById(R.id.plus_hour);
        plusDurationhours.setOnClickListener(view1 -> startHourD.plusHour());

        minusDurationhours = view.findViewById(R.id.minus_hour);
        minusDurationhours.setOnClickListener(view1 -> startHourD.minusHour());

        plusDurationminuts = view.findViewById(R.id.plus_minute);
        plusDurationminuts.setOnClickListener(view1 -> startMinuteD.plusMinute());

        minusDurationminuts = view.findViewById(R.id.minus_minute);
        minusDurationminuts.setOnClickListener(view1 -> startMinuteD.minusMinute());


    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = requireDialog();
        dialog.getWindow().setLayout(MATCH_PARENT, MATCH_PARENT);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.Theme_SDaily);
    }

    public void saveNote() {
        beginDayMls = StaticValues.getDayMls();
        long t = Calendar.getInstance().getTime().getTime();
        duration = startHourD.getHour() * 3600000L + startMinuteD.getMinute() * 60000L;
        long startTime = startHour.getHour() * 3600000L + startMinute.getMinute() * 60000L;
        beginDateMls = beginDayMls + startTime;
        time = timeFormat.format(new Date(beginDateMls));

        nd = db.noteDao();
        String date = StaticValues.getStringDate();

        newNote = new NoteEntity();
        newNote.title = title.getText().toString();
        newNote.description = description.getText().toString();
        newNote.type = type;
        newNote.missed = missed;
        newNote.notified = notified;
        newNote.postNotified = postNotified;
        newNote.startHour = startHour.getHour();
        newNote.startMinute = startMinute.getMinute();
        newNote.todoin = todoin;
        newNote.duration = duration;
        newNote.finished = finished;
        newNote.time = time;
        newNote.date = date;
        newNote.beginDateMls = beginDateMls;
        newNote.beginDayMls = beginDayMls;
        newNote.dayOfWeek = StaticValues.dayOfWeek;
        newNote.endHour = setEndHour(startHour.getHour() + startHourD.getHour());
        newNote.endMinute = setEndMinute(startMinute.getMinute() + startMinuteD.getMinute());


        new Thread(() -> nd.insert(newNote)).start();

    }

    private int setEndHour(int i) {
        if (i > 23) {
            return i - 24;
        } else {
            return i;
        }
    }

    private int setEndMinute(int i) {
        if (i > 55) {
            newNote.endHour += 1;
            return i - 60;
        } else {
            return i;
        }
    }

}



