package ru.tanec.sdaily;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RangeFragment extends DialogFragment {

    TimeTableItem obj;
    RecyclerView dialog_recycler;
    DialogAdapter recycler_adapter;
    ImageButton add_btn;
    ImageButton accept_btn;
    TimeTableDao td;
    DataBase db;
    RangeItem[] data;

    public RangeFragment() {
        super(R.layout.fragment_range);
    }

    public RangeFragment(TimeTableItem obj) {
        super(R.layout.fragment_range);
        this.obj = obj;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_range, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dialog_recycler = view.findViewById(R.id.time_dialog_recycler);
        add_btn = view.findViewById(R.id.dialog_add_btn);
        accept_btn = view.findViewById(R.id.dialog_accept_btn);

        new Thread(() -> {
            db = DataBaseApl.getInstance().getDatabase();
            td = db.timeTableDao();
            TimeTableEntity a;
            try {
                a = td.getById(obj.id);
            } catch (Exception e) {
                a = new TimeTableEntity();
                a.title = obj.title;
                a.id = obj.id;
                a.timerange = new RangeItem[1];
                a.timerange[0].id = 0;
                td.insert(a);
            }
            data = a.timerange;
            new Handler(Looper.getMainLooper()).post(() -> {
                add_btn.setOnClickListener(v -> addRange());
                recycler_adapter = new DialogAdapter(requireContext(), data);
                dialog_recycler.setAdapter(recycler_adapter);
            });
        }).start();

        accept_btn.setOnClickListener(e -> {
            this.onDestroy();
        });
    }

    public void addRange() {
        RangeItem nri = new RangeItem();
        recycler_adapter.addItem(nri);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        data = recycler_adapter.list;
        int id = obj.id;
        new Thread(() -> {
            db = DataBaseApl.getInstance().getDatabase();
            td = db.timeTableDao();
            TimeTableEntity ne = new TimeTableEntity();
            ne.timerange = data;
            ne.id = id;
            ne.title = obj.title;
            td.update(ne);
        }).start();
    }
}