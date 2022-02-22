package ru.tanec.sdaily.adapters.items;

public class RangeItem extends ItemList {
    public int start_hour = 0;
    public int start_minute = 0;
    public int end_minute = 0;
    public int end_hour = 0;
    public int id;
    public int deleted = 1;
    public String title = "Untitled";

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

    public int[] getDuration() {
        int d_hour = end_hour - start_hour;
        int d_minute = Math.abs(end_minute - start_minute);
        return new int[]{d_hour, d_minute};
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
