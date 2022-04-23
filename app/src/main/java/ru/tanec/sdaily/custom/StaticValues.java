package ru.tanec.sdaily.custom;

import androidx.arch.core.internal.SafeIterableMap;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.tanec.sdaily.adapters.items.NoteDataItem;

public class StaticValues {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static Date viewDate = Calendar.getInstance().getTime();
    public static String stringDate;
    public static MutableLiveData<Date> liveDate = new MutableLiveData<>();
    public static int dayOfWeek;
    public static List<NoteDataItem> data = new ArrayList<>();

    public static void setViewDate(Date viewDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(viewDate);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MILLISECOND, 0);
        StaticValues.viewDate = cal.getTime();
        StaticValues.liveDate.setValue(StaticValues.viewDate);
        stringDate = sdf.format(viewDate);
        setDayOfWeek(cal.get(Calendar.DAY_OF_WEEK));
    }

    public static Date getViewDate() {
        return viewDate;
    }

    public static String getStringDate() {
        return stringDate;
    }

    private static void setDayOfWeek(int day) {
        dayOfWeek = day;
    }

    public static long getDayMls() { return viewDate.getTime(); }
}
