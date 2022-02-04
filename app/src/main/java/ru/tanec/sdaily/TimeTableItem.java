package ru.tanec.sdaily;

public class TimeTableItem extends ItemList {

    public TimeTableItem(String title) {
        this.title = title;
    }

    String title;
    Boolean[] fill;

    void setFill(Boolean[] fill) {
        this.fill = fill;
    }
}
