package ru.tanec.sdaily.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import ru.tanec.sdaily.adapters.items.NoteDataItem;
import ru.tanec.sdaily.database.DataBase;
import ru.tanec.sdaily.database.DataBaseApl;
import ru.tanec.sdaily.database.NoteDao;
import ru.tanec.sdaily.database.NoteEntity;
import ru.tanec.sdaily.helpers.GestureHelper;
import ru.tanec.sdaily.R;
import ru.tanec.sdaily.adapters.CalendarAdapter;

public class CollapsibleCalendar extends LinearLayout {
    LinearLayout header;
    ImageView btnToday;
    ImageView btnPrev;
    ImageView btnNext;
    TextView month;
    TextView year;
    GridView gridView;
    ImageView collapseButton;
    ConstraintLayout container;

    String dayOfMonth;
    Date today;
    Calendar currentDate = Calendar.getInstance();
    Calendar selectedDate = Calendar.getInstance();
    int DAY_COUNT = 7;
    int DIVIDER = Calendar.WEEK_OF_YEAR;

    Resources res = getResources();
    String[] month_name = res.getStringArray(R.array.months);

    HashMap<Float, Float> heightCollapse = new HashMap<Float, Float>();
    Float HEIGHT_VISIBLE = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 192,  getResources().getDisplayMetrics());
    Float HEIGHT_HIDDEN = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48,  getResources().getDisplayMetrics());
    Float currentHeight = HEIGHT_HIDDEN;

    DataBase db = DataBaseApl.instance.getDatabase();

    @SuppressLint("ClickableViewAccessibility")
    public CollapsibleCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);


        // Linking support of gestures
        GestureHelper gestureHelper = new GestureHelper(context) {
            @Override
            public void onSwipeLeft() {
                selectedDate.add(DIVIDER, 1);
                updateCalendar(new HashSet<>());
            }

            @Override
            public void onSwipeRight() {
                selectedDate.add(DIVIDER, -1);
                updateCalendar(new HashSet<>());
            }
        };

        heightCollapse.put(HEIGHT_HIDDEN, HEIGHT_VISIBLE);
        heightCollapse.put(HEIGHT_VISIBLE, HEIGHT_HIDDEN);
        today = new Date();
        StaticValues.setViewDate(today);
        currentDate.setTime(today);
        initControl(context, attrs);

        gridView.setOnTouchListener(gestureHelper);
    }

    private void assignUiElements() {
        header = findViewById(R.id.calendar_header);
        btnPrev = findViewById(R.id.calendar_prev_button);
        btnNext = findViewById(R.id.calendar_next_button);
        year = findViewById(R.id.calendar_year);
        month = findViewById(R.id.calendar_month);
        btnToday = findViewById(R.id.calendar_today);
        collapseButton = findViewById(R.id.collapse_button);
        gridView = findViewById(R.id.calendar_grid);
        container = findViewById(R.id.calendar_container);

        container.setMaxHeight((int) (float) currentHeight);

        collapseButton.animate().scaleX(1.2f);
        collapseButton.animate().scaleY(-1f);

        collapseButton.setOnClickListener(l -> {
            currentHeight = heightCollapse.get(currentHeight);
            int h = (int) (float) currentHeight;
            if (DAY_COUNT != 35) {
                DAY_COUNT = 35;
                DIVIDER = Calendar.MONTH;
                collapseButton.animate().scaleY(1f);
            } else {
                DAY_COUNT = 7;
                DIVIDER = Calendar.WEEK_OF_YEAR;
                collapseButton.animate().scaleY(-1f);
            }
            container.setMaxHeight(h);

            updateCalendar(new HashSet<>());
        });

        btnNext.setOnClickListener(l -> {
            selectedDate.add(DIVIDER, 1);
            StaticValues.setViewDate(selectedDate.getTime());
            updateCalendar(new HashSet<>());
        });

        btnPrev.setOnClickListener( l-> {
            selectedDate.add(DIVIDER, -1);
            StaticValues.setViewDate(selectedDate.getTime());
            updateCalendar(new HashSet<>());
        });

        btnToday.setOnClickListener(l-> {
            selectedDate.setTime(today);
            StaticValues.setViewDate(selectedDate.getTime());
            updateCalendar(new HashSet<>());
        });
    }


    private void initControl(Context context, AttributeSet attrs)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.collapsible_calendar, this);
        assignUiElements();

        updateCalendar(new HashSet<Date>());
    }

    public void updateCalendar(HashSet<Date> events) {
        selectedDate.setTime(StaticValues.getViewDate());

        ArrayList<Boolean> notes = new ArrayList<>();
        ArrayList<Date> cells = new ArrayList<>();

        NoteDao nd = db.noteDao();

        int divider = Calendar.DAY_OF_MONTH;

        if (DAY_COUNT == 7) {
            divider = Calendar.DAY_OF_WEEK;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(StaticValues.getViewDate());

        if (selectedDate.get(Calendar.WEEK_OF_YEAR) != currentDate.get(Calendar.WEEK_OF_YEAR)) {
            btnToday.setImageResource(R.drawable.calendar2);
        } else {
            btnToday.setImageResource(R.drawable.calendar1);
        }
        if (divider == Calendar.DAY_OF_MONTH) {
        calendar.set(divider, 1);
        } else {
            calendar.set(divider, Calendar.WEEK_OF_MONTH);
        }
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 2;

        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        int finalDivider = divider;
        new Thread(() -> {
            while (cells.size() < DAY_COUNT)
            {
                calendar.set(Calendar.HOUR, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                NoteEntity[] ne = nd.getByDate(calendar.getTime().getTime());
                notes.add((ne.length != 0));
                cells.add(calendar.getTime());
                calendar.add(finalDivider, 1);
            }
            new Handler(Looper.getMainLooper()).post(() -> {
                gridView.setAdapter(new CalendarAdapter(getContext(), cells, selectedDate, notes));
            });
        }).start();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String[] dateToday = sdf.format(selectedDate.getTime()).split("-");
        month.setText(month_name[Integer.parseInt(dateToday[1])]);
        year.setText(dateToday[0]);
        dayOfMonth = dateToday[2];
    }
}


