package ru.tanec.sdaily.adapters;

import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.graphics.Color;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ru.tanec.sdaily.R;
import ru.tanec.sdaily.custom.StaticValues;

public class CalendarAdapter extends ArrayAdapter<Date> {
    private LayoutInflater inflater;

    Calendar selectedDate;
    View selectedDay;
    ArrayList<Boolean> notes;

    public CalendarAdapter(Context context, ArrayList<Date> days, Calendar selectedDate, ArrayList<Boolean> notes)
    {
        super(context, R.layout.custom_calendar_day, days);
        this.selectedDate = selectedDate;
        inflater = LayoutInflater.from(context);
        this.notes = notes;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        // Date of cell
        Calendar calendar = Calendar.getInstance();
        Date date = getItem(position);
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        // Date today
        Date today = new Date();
        Calendar calendarToday = Calendar.getInstance();
        calendarToday.setTime(today);

        // Setting parameters if view is null
        if (view == null)
            view = inflater.inflate(R.layout.custom_calendar_day, parent, false);
            ((TextView)view.findViewById(R.id.date)).setTypeface(null, Typeface.NORMAL);
            ((TextView)view.findViewById(R.id.date)).setTextColor(Color.BLACK);


        if (month == selectedDate.get(Calendar.MONTH) &
            year == selectedDate.get(Calendar.YEAR) & month == calendarToday.get(Calendar.MONTH)) {
            ((TextView) view.findViewById(R.id.date)).setTextColor(Color.parseColor("#000000"));
            ((TextView) view.findViewById(R.id.date)).setGravity(Gravity.CENTER);
            if (day == selectedDate.get(Calendar.DATE)) {
                view.findViewById(R.id.date).setBackgroundResource(R.drawable.current_date);
                selectedDay= view;
            }
        }
        else if (month != calendarToday.get(Calendar.MONTH) & month != selectedDate.get(Calendar.MONTH)) {
            ((TextView) view.findViewById(R.id.date)).setTextColor(Color.parseColor("#813D9EFF"));
        }
        else if (month == selectedDate.get(Calendar.MONTH)) {
            ((TextView) view.findViewById(R.id.date)).setTextColor(Color.parseColor("#0a85ff"));
        }

        if (month == calendarToday.get(Calendar.MONTH) &
                day == calendarToday.get(Calendar.DATE)    &
                year == calendarToday.get(Calendar.YEAR)) {
            ((TextView)view.findViewById(R.id.date)).setTextColor(Color.parseColor("#ebebeb"));
        }


            // Checking if date of cell is today
       /* if (month != calendarToday.get(Calendar.MONTH) || year != calendarToday.get(Calendar.YEAR)) {
            if(month != selectedDate.get(Calendar.MONTH)) {
                ((TextView) view.findViewById(R.id.date)).setTextColor(Color.parseColor("#813D9EFF"));
            } else {
                ((TextView) view.findViewById(R.id.date)).setTextColor(Color.parseColor("#0a85ff"));
            }

        } else if (day == calendarToday.get(Calendar.DATE)) {
            ((TextView)view.findViewById(R.id.date)).setTextColor(Color.parseColor("#ebebeb"));
            ((TextView) view.findViewById(R.id.date)).setGravity(Gravity.CENTER);
        } if (day == selectedDate.get(Calendar.DATE)) {
            ((TextView) view.findViewById(R.id.date)).setGravity(Gravity.CENTER);
            view.findViewById(R.id.date).setBackgroundResource(R.drawable.current_date);
            selectedDay = view;
        }*/

        ((TextView)view.findViewById(R.id.date)).setText(String.valueOf(day));


        // onClickListener. Setting selectedDay into StaticValues
        view.findViewById(R.id.date).setOnClickListener(view1 -> {
            view1.findViewById(R.id.date).setBackgroundResource(R.drawable.current_date);
            if (null != selectedDay & selectedDay != view1) {
                selectedDay.findViewById(R.id.date).setBackgroundResource(0);
            }
            selectedDay = view1;
            StaticValues.setViewDate(date);
            selectedDate.setTime(StaticValues.getViewDate());
        });

        if (!notes.get(position)) {
            view.findViewById(R.id.top_dot).setVisibility(View.GONE);
        }

        return view;
    }
}
