package ru.tanec.sdaily.adapters.items;

import java.util.HashMap;
import java.util.Map;

public class RangeItem extends ItemList {
    public Integer start_hour = 0;
    public Integer start_minute = 0;
    public Integer end_minute = 0;
    public Integer end_hour = 0;
    public int id;
    public int deleted = 1;
    public String title = null;
    Map<String,String> dictionary = new HashMap<String,String>();



    public void setStartTime(int hour, int minute) {
        start_hour = hour;
        start_minute = minute;
        if (end_hour < start_hour) {
            end_hour = hour;
        }
    }

    public void setEndTime(int hour, int minute) {
        if (hour > start_hour) end_hour = hour;
        end_minute = minute;
    }

    public int[] getStartTime() {
        return new int[]{start_hour, start_minute};
    }

    public int[] getEndTime() {
        return new int[]{end_hour, end_minute};

    }

    public long getDuration() {
        int d_hour = end_hour - start_hour;
        int d_minute = end_minute - start_minute;
        if (d_minute < 0) {
            d_minute = 60 + d_minute;
            d_hour -= 1;
        }
        return d_hour * 3600000L + d_minute * 60000L;
    }

    public String getStringDuration() {
        String s_h = start_hour.toString();
        String s_m = start_minute.toString();
        String e_h = end_hour.toString();
        String e_m = end_minute.toString();
        if (s_h.length() < 2) {
            s_h = "0" + s_h;
        }
        if (s_m.length() < 2) {
            s_m = "0" + s_m;
        }
        if (e_h.length() < 2) {
            e_h = "0" + e_h;
        }
        if (e_m.length() < 2) {
            e_m = "0" + e_m;
        }
        return s_h + ":" + s_m + " - " + e_h + ":" + e_m;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void delete() {
        this.deleted *= -1;
    }
}
