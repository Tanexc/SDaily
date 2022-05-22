package ru.tanec.sdaily.fragments;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.app.Dialog;
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
import android.view.WindowManager;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;

import java.util.logging.Logger;

import ru.tanec.sdaily.R;
import ru.tanec.sdaily.adapters.items.RangeItem;
import ru.tanec.sdaily.adapters.items.TimeTableItem;
import ru.tanec.sdaily.adapters.DialogAdapter;
import ru.tanec.sdaily.database.DataBase;
import ru.tanec.sdaily.database.DataBaseApl;
import ru.tanec.sdaily.database.TimeTableDao;
import ru.tanec.sdaily.database.TimeTableEntity;


public class RangeFragment extends DialogFragment {

    TimeTableItem obj;
    RecyclerView dialog_recycler;
    DialogAdapter recycler_adapter;
    MaterialButton exitButton;
    MaterialButton addButton;
    TimeTableDao td;
    DataBase db;
    boolean destroyed;
    RangeItem[] data;

    public RangeFragment() {
        super(R.layout.fragment_range);
    }

    public RangeFragment(TimeTableItem obj) {
        super(R.layout.fragment_range);
        this.obj = obj;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.getWindow().setLayout(MATCH_PARENT, MATCH_PARENT);


        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.AppTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_range, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = requireDialog();
        dialog.getWindow().setLayout(MATCH_PARENT, MATCH_PARENT);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialog_recycler = view.findViewById(R.id.time_dialog_recycler);
        addButton = view.findViewById(R.id.add_button);
        exitButton = view.findViewById(R.id.exit);
        new Thread(() -> {
            db = DataBaseApl.instance.getDatabase();
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
                addButton.setOnClickListener(v -> addRange());
                recycler_adapter = new DialogAdapter(requireContext(), data);
                dialog_recycler.setAdapter(recycler_adapter);
            });
        }).start();

        exitButton.setOnClickListener(e -> {
            this.getParentFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss();
        });
    }

    public void addRange() {
        RangeItem nri = new RangeItem();
        recycler_adapter.addItem(nri);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        data = recycler_adapter.getList();
        int id = obj.id;
        new Thread(() -> {
            db = DataBaseApl.instance.getDatabase();
            td = db.timeTableDao();
            TimeTableEntity ne = new TimeTableEntity();

            for (int i = 0; i < data.length; i++) {
                if (data[i].title == null | data[i].title == "") {
                    data[i].title = "u$n$t$i$t$l$e$d";
                }
            }
            ne.timerange = data;
            ne.id = id;
            String title = obj.title;
            if (title == "") {
                title = "null";
            }
            ne.title = obj.title;
            td.update(ne);
        }).start();
    }
}