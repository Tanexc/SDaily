package ru.tanec.sdaily.adapters.items;

public class TimeTableItem extends ItemList {

    public TimeTableItem(String title) {
        this.title = title;
    }

    public String title;
    public Boolean[] fill;

    public void setFill(Boolean[] fill) {
        this.fill = fill;
    }
}
