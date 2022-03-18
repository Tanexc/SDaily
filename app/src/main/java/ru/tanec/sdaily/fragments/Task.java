package ru.tanec.sdaily.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ru.tanec.sdaily.R;
import ru.tanec.sdaily.adapters.GoalAdapter;
import ru.tanec.sdaily.adapters.items.NoteDataItem;
import ru.tanec.sdaily.custom.StaticValues;
import ru.tanec.sdaily.database.DataBase;
import ru.tanec.sdaily.database.DataBaseApl;
import ru.tanec.sdaily.database.NoteDao;
import ru.tanec.sdaily.database.NoteEntity;

public class Task extends Fragment {
    FragmentActivity activity;
    RecyclerView goalrecycler;
    Context context;
    GoalAdapter adapter = new GoalAdapter(context, activity, Arrays.asList(new NoteDataItem[0]));;
    DataBase db = DataBaseApl.instance.getDatabase();

    public Task() {
        super(R.layout.fragment_task);
    }

    public Task(Context context) {
        super(R.layout.fragment_task);
        this.context = requireContext();
        this.activity = requireActivity();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        goalrecycler = view.findViewById(R.id.goal_recycler);

        new Thread(() -> {
            NoteDao nd = db.noteDao();
            NoteEntity[] primaryData = nd.getByDate(StaticValues.viewDate.getTime());
            List<NoteDataItem> data = new ArrayList<>(primaryData.length);
            for (int i = 0; i < primaryData.length; i++) {
                data.add(new NoteDataItem());
                data.get(i).setFromEntity(primaryData[i]);
            }
            Collections.sort(data, (t1, t2) -> {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH-mm");
                    Date date1 = sdf.parse(t1.getTime());
                    Date date2 = sdf.parse(t2.getTime());
                    return date1 != null ? date1.compareTo(date2) : 0;
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
            });

//            Collections.sort(data, (type1, type2) -> {
//                try {
//                    SimpleDateFormat sdf = new SimpleDateFormat("HH-mm");
//                    Date date1 = sdf.parse(type1.gettu());
//                    Date date2 = sdf.parse(type2.getTime());
//                    return date1 != null ? date1.compareTo(date2) : 0;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    return 0;
//                }
//            });

            new Handler(Looper.getMainLooper()).post(() -> {
                adapter = new GoalAdapter(context, activity, data);
                goalrecycler.setAdapter(adapter);
            });

        }).start();


        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MILLISECOND, 0);

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}