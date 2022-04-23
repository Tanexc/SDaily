package ru.tanec.sdaily.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.tanec.sdaily.R;
import ru.tanec.sdaily.adapters.items.RangeItem;
import ru.tanec.sdaily.adapters.items.TimeTableItem;
import ru.tanec.sdaily.adapters.TimeAdapter;
import ru.tanec.sdaily.database.DataBase;
import ru.tanec.sdaily.database.DataBaseApl;
import ru.tanec.sdaily.database.TimeTableDao;
import ru.tanec.sdaily.database.TimeTableEntity;

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
        ArrayList<TimeTableItem> data = new ArrayList<TimeTableItem>();
        timetableRecycler = view.findViewById(R.id.timetable_recycler);
        TimeAdapter adapter = new TimeAdapter(requireContext(), requireActivity(), data, id_title);
        timetableRecycler.setAdapter(adapter);
        adapter.setData(data);
        db = DataBaseApl.instance.getDatabase();
        td = db.timeTableDao();
        td.getAll().observe(requireActivity(), timeTableEntities -> {
            for (TimeTableEntity item: timeTableEntities) {
                int id = Integer.parseInt("" + item.id);
                TimeTableItem it = new TimeTableItem(item.title);
                it.id = id;
                it.setFill(getFillFromTm(item.timerange));
                adapter.changeDataItem(it, id);

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
