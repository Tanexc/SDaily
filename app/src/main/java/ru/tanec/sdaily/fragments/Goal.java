package ru.tanec.sdaily.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.icu.util.LocaleData;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import ru.tanec.sdaily.adapters.items.NoteDataItem;
import ru.tanec.sdaily.R;
import ru.tanec.sdaily.adapters.GoalAdapter;
import ru.tanec.sdaily.custom.StaticValues;
import ru.tanec.sdaily.database.DataBase;
import ru.tanec.sdaily.database.DataBaseApl;
import ru.tanec.sdaily.database.NoteDao;
import ru.tanec.sdaily.database.NoteEntity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yariksoffice.lingver.Lingver;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Goal extends Fragment {

    FragmentActivity activity;
    Context context;
    RecyclerView goalrecycler;
    DataBase db = DataBaseApl.instance.getDatabase();
    GoalAdapter adapter;

    ImageView filterButton;
    ImageView sortByTime;
    ImageView sortByTitle;
    ImageView sortByType;
//    Button Language;
//    Integer lcnt =0;

    int state = View.VISIBLE;

    public Goal() {
        super(R.layout.fragment_goal);
    }

    public Goal(Context context) {
        super(R.layout.fragment_goal);
        this.context = requireContext();
        this.activity = requireActivity();

    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        goalrecycler = view.findViewById(R.id.goal_recycler);
        adapter = new GoalAdapter(context, activity, new ArrayList<NoteDataItem>());
        goalrecycler.setAdapter(adapter);

        filterButton = view.findViewById(R.id.filterButton);
        sortByTime = view.findViewById(R.id.sortByTime);
        sortByTitle = view.findViewById(R.id.sortByTitle);
        sortByType = view.findViewById(R.id.sortByType);

//        Language = view.findViewById(R.id.language);
//        Language.setOnClickListener(view1 -> {
//            lcnt++;
//            if (lcnt==1){
//                Lingver.getInstance().setLocale(view1.getContext(),"tt");
//                Intent intent = activity.getIntent();
//                activity.finish();
//                startActivity(intent);
//            }
//            else{
//                Lingver.getInstance().setLocale(view1.getContext(),"ru");
//                lcnt=0;
//                Intent intent = activity.getIntent();
//                activity.finish();
//                startActivity(intent);
//            }
//        });



        new Thread(() -> {
            NoteEntity[] f = db.noteDao().getByDate(StaticValues.getDayMls());
            List<NoteDataItem> nd = new ArrayList<NoteDataItem>();
            for (NoteEntity note : f) {
                NoteDataItem n = new NoteDataItem();
                n.setFromEntity(note);
                nd.add(n);
            }
            new Handler(Looper.getMainLooper()).post(() -> {
                synchronized (adapter) {
                    adapter.setList(nd);
                    adapter.notifyDataSetChanged();
                }

            });

        }).start();

        LiveData<List<NoteEntity>> nt = db.noteDao().getLiveByDate(StaticValues.getDayMls());
        nt.observe(this.getActivity(), noteEntities -> {
            List<NoteDataItem> nd = new ArrayList<NoteDataItem>();
            for (NoteEntity note: noteEntities) {
                NoteDataItem n = new NoteDataItem();
                n.setFromEntity(note);
                nd.add(n);
            }
            synchronized (adapter) {
                adapter.setList(nd);
            }
        });


        filterButton.setOnClickListener(l -> {
            sortByTime.setVisibility(state);
            sortByType.setVisibility(state);
            sortByTitle.setVisibility(state);
            sortByType.setBackgroundColor(R.color.fragment_background);
            if (state == View.GONE) {
                state = View.VISIBLE;
                filterButton.setImageResource(R.drawable.filter_out);
            } else {
                state = View.GONE;
                filterButton.setImageResource(R.drawable.filter);
            }
        });

        sortByType.setOnClickListener(l -> {
            sortByType.setBackgroundColor(R.color.day_fill);
            sortByTime.setBackgroundColor(R.color.fragment_background);
            sortByTitle.setBackgroundColor(R.color.fragment_background);
            List<NoteDataItem> list = adapter.getList();
            Collections.sort(list, (t1, t2) -> {
                return Integer.compare(t1.type, t2.type);
            });
            adapter.setList(list);
        });

        sortByTime.setOnClickListener(l -> {
            sortByType.setBackgroundColor(R.color.fragment_background);
            sortByTime.setBackgroundColor(R.color.day_fill);
            sortByTitle.setBackgroundColor(R.color.fragment_background);
            List<NoteDataItem> list = adapter.getList();
            Collections.sort(list, (t1, t2) -> {
                return Long.compare(t1.beginDateMls, t2.beginDateMls);
            });
            adapter.setList(list);
        });

        sortByTitle.setOnClickListener(l -> {
            sortByType.setBackgroundColor(R.color.fragment_background);
            sortByTitle.setBackgroundColor(R.color.day_fill);
            sortByTime.setBackgroundColor(R.color.fragment_background);
            List<NoteDataItem> list = adapter.getList();
            Collections.sort(list, (t1, t2) -> {
                return (t1.title.compareTo(t2.title));
            });
            adapter.setList(list);
        });


        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(v -> {
            MakeGoals fragment = new MakeGoals();
            fragment.show(requireActivity().getSupportFragmentManager(), "makeGoal");
        });



    }
}