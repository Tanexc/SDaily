package ru.tanec.sdaily;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.graphics.Color;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

class CalendarAdapter extends ArrayAdapter<Date> {
    private LayoutInflater inflater;

    HashSet<Date> eventDays;
    Calendar selectedDate;

    public CalendarAdapter(Context context, ArrayList<Date> days, HashSet<Date> eventDays, Calendar selectedDate)
    {
        super(context, R.layout.custom_calendar_day, days);
        this.eventDays = eventDays;
        this.selectedDate = selectedDate;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {

        Calendar calendar = Calendar.getInstance();
        Date date = getItem(position);
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        // today
        Date today = new Date();
        Calendar calendarToday = Calendar.getInstance();
        calendarToday.setTime(today);

        if (view == null)
            view = inflater.inflate(R.layout.custom_calendar_day, parent, false);
        ((TextView)view).setTypeface(null, Typeface.NORMAL);
        ((TextView)view).setTextColor(Color.BLACK);

        if (month != calendarToday.get(Calendar.MONTH) || year != calendarToday.get(Calendar.YEAR)) {
            if(month != selectedDate.get(Calendar.MONTH)) {
                ((TextView) view).setTextColor(Color.parseColor("#813D9EFF"));
            } else {
                ((TextView) view).setTextColor(Color.parseColor("#0a85ff"));
            }

        } else if (day == calendarToday.get(Calendar.DATE)) {

            ((TextView)view).setTextColor(Color.parseColor("#ebebeb"));
            ((TextView) view).setGravity(Gravity.CENTER);
            view.setBackgroundResource(R.drawable.selected_date);
        }

        // set text
        ((TextView)view).setText(String.valueOf(calendar.get(Calendar.DATE)));

        return view;
    }
}
