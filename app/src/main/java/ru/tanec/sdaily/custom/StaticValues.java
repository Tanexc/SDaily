package ru.tanec.sdaily.custom;

import androidx.arch.core.internal.SafeIterableMap;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Calendar;
import java.util.Date;

public class StaticValues {
    public static Date viewDate = Calendar.getInstance().getTime();
    public static MutableLiveData<Date> liveDate = new MutableLiveData<>();



    public static void setViewDate(Date viewDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(viewDate);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MILLISECOND, 0);
        StaticValues.viewDate = cal.getTime();
        StaticValues.liveDate.setValue(StaticValues.viewDate);
    }

    public static Date getViewDate() {
        return viewDate;
    }
}
