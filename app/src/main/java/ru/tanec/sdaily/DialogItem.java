package ru.tanec.sdaily;

public class DialogItem extends ItemList {
    Integer start_hour;
    Integer start_minute;
    Integer end_minute;
    Integer end_hour;

    public void setStartTime(Integer hour, Integer minute) {
        start_hour = hour;
        start_minute = minute;
        if (end_hour < start_hour) {
            end_hour = hour;
        }
    }

    public void setEndTime(Integer hour, Integer minute) {
        if (hour > start_hour) end_hour = hour;
        end_minute = minute;
    }

    public Integer[] getStartTime() {
        return new Integer[]{start_hour, start_minute};
    }

    public Integer[] getEndTime() {
        return new Integer[]{end_hour, end_minute};
    }

    public int[] getDuration() {
        int d_hour = end_hour - start_hour;
        int d_minute = Math.abs(end_minute - start_minute);
        return new int[]{d_hour, d_minute};
    }

}
