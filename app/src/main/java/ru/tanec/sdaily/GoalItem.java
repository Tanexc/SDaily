package ru.tanec.sdaily;


public class GoalItem extends ItemList{
    public GoalItem(String title) {
        this.title = title;
    }

    String title;
    Boolean[] fill;

    void setFill(Boolean[] fill) {
        this.fill = fill;
    }
}


