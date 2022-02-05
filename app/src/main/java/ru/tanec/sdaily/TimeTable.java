package ru.tanec.sdaily;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Map;

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
        new Thread(() -> {
            db = DataBaseApl.getInstance().getDatabase();
            td = db.timeTableDao();
            for (int i = 0; i < 7; i++) {
                TimeTableEntity d;
                try {
                    d = td.getById(i);
                } catch (Exception e) {
                    d = null;
                }
                if (d != null) {
                    data[i] = new TimeTableItem(d.title);
                    data[i].id = i;
                    data[i].setFill(getFillFromTm(d.timerange));
                } else {
                    d = new TimeTableEntity();
                    d.title = id_title[i];
                    d.id = i;
                    td.update(d);
                }
            }
        }).start();

        timetableRecycler.setAdapter(new TimeAdapter(requireContext(), requireActivity(), data, id_title));
    }

    public Boolean[] getFillFromTm(DialogItem[] dt) {
        Boolean[] fill = new Boolean[24];
        for (DialogItem dialogItem : dt) {
            if (dialogItem != null) {
            int s = dialogItem.getStartTime()[0];
            int e = dialogItem.getEndTime()[0];
            for (int k = s; k < e + 1; k++) {
                fill[k] = true;
            }
            }
        }
        return fill;
    }
}