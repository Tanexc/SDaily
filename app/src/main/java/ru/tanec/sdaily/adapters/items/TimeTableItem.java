package ru.tanec.sdaily.adapters.items;

import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TimeTableItem extends ItemList {

    public TimeTableItem(String title) {
        this.title = title;
    }

    public String title;
    public Boolean[] fill;
    public RangeItem[] timerange;
    private boolean intersections;

    public void setFill(Boolean[] fill) {
        this.fill = fill;
    }

    public RangeItem[] getTimerange() {
        return timerange;
    }

    public void setTimerange(RangeItem[] timerange) {
        this.timerange = timerange;
        if (timerange.length == 0 | timerange[0] == null) {
            RangeItem r = new RangeItem();
            this.timerange = new RangeItem[]{r};
        }
        checkForIntersections();
    }

    public boolean intersects() {
        checkForIntersections();
        return intersections;
    }

    public void checkForIntersections() {
        List<RangeItem> ranges = Arrays.asList(timerange);
        Collections.sort(ranges, (item, item2) -> {
            if (item.start_hour > item2.start_hour) {
                return 0;
            } else if (item.start_hour.equals(item2.start_hour)) {
                if (item.start_minute >= item2.start_minute) {
                    return 0;
                }
            }

            return 1;
        });
        intersections = false;
        for (int i = 1; i < ranges.size(); i++) {
            if (ranges.get(i).start_hour < ranges.get(i - 1).end_hour) {
                intersections = true;
                i = ranges.size();
            } else if (ranges.get(i).start_hour.equals(ranges.get(i - 1).end_hour) & ranges.get(i).start_minute < ranges.get(i - 1).end_minute) {
                intersections = true;
                i = ranges.size();
            }
        }

    }

    public ArrayList<String[]> getStringRanges() {
        ArrayList<String[]> res = new ArrayList<>();
        for (RangeItem r: timerange) {
            res.add(r.toData());
        }
        return res;
    }
}
