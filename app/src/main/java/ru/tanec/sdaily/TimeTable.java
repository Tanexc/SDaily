package ru.tanec.sdaily;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TimeTable extends Fragment {

    FragmentActivity activity;
    Context context;
    RecyclerView timetableRecycler;
    TimeTableDao td;
    DataBase db;
    String[] id_title = new String[]{"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"};

    public TimeTable() {
        super(R.layout.fragment_timetable);
    }

    public TimeTable(Context context) {
        super(R.layout.fragment_timetable);
        this.context = requireContext();
        this.activity = requireActivity();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        timetableRecycler = view.findViewById(R.id.timetable_recycler);
        TimeTableItem[] data = new TimeTableItem[7];
            db = DataBaseApl.getInstance().getDatabase();
            td = db.timeTableDao();
            td.getAll().observe(requireActivity(), new Observer<List<TimeTableEntity>>() {
                @Override
                public void onChanged(List<TimeTableEntity> timeTableEntities) {
                    for (TimeTableEntity item: timeTableEntities) {
                        int id = Integer.parseInt("" + item.id);
                        data[id] = new TimeTableItem(item.title);
                        data[id].id = id;
                        data[id].setFill(getFillFromTm(item.timerange));
                        timetableRecycler.setAdapter(new TimeAdapter(requireContext(), requireActivity(), data, id_title));
                    }
                }
            });
    }

    public Boolean[] getFillFromTm(RangeItem[] dt) {
        Boolean[] fill = new Boolean[24];
        for (RangeItem rangeItem : dt) {
            if (rangeItem != null) {
                int start = rangeItem.getStartTime()[0];
                int end = rangeItem.getEndTime()[0];
                int end_minute = rangeItem.getEndTime()[1];
                if (end_minute > 40) {
                    end += 1;
                } else if (20 < end_minute & end_minute < 40) {
                    fill[end] = null;
                }
                for (int k = start; k < end; k++) {
                    fill[k] = true;
                }
            }
        }
        return fill;
    }
}

