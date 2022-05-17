package ru.tanec.sdaily.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import ru.tanec.sdaily.R;
import ru.tanec.sdaily.adapters.GoalAdapter;
import ru.tanec.sdaily.adapters.items.NoteDataItem;
import ru.tanec.sdaily.database.DataBase;
import ru.tanec.sdaily.database.DataBaseApl;
import ru.tanec.sdaily.database.NoteEntity;
import ru.tanec.sdaily.helpers.NoteDiffUtil;
import ru.tanec.sdaily.helpers.SimpleItemTouchHelperCallback;

public class Task extends Fragment {

    FragmentActivity activity;
    Context context;
    RecyclerView goalrecycler;
    DataBase db = DataBaseApl.instance.getDatabase();
    GoalAdapter adapter;

    ImageView filterButton;
    ImageButton sortByTime;
    ImageButton sortByTitle;
    ImageButton sortByType;

    int state = View.VISIBLE;

    public Task() {
        super(R.layout.fragment_task);
    }

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

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MILLISECOND, 0);


        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(goalrecycler);

        new Thread(() -> {
            NoteEntity[] f = db.noteDao().getByDate(cal.getTime().getTime());
            List<NoteDataItem> nd = new ArrayList<NoteDataItem>();
            for (NoteEntity note : f) {
                NoteDataItem n = new NoteDataItem();
                n.setFromEntity(note);
                nd.add(n);
            }
            new Handler(Looper.getMainLooper()).post(() -> {
                synchronized (adapter) {
                    adapter.setList(nd);
                }

            });

        }).start();

        LiveData<List<NoteEntity>> nt = db.noteDao().getLiveByDate(cal.getTime().getTime());
        nt.observe(this.getActivity(), noteEntities -> {
            List<NoteDataItem> nd = new ArrayList<NoteDataItem>();
            for (NoteEntity note: noteEntities) {
                NoteDataItem n = new NoteDataItem();
                n.setFromEntity(note);
                nd.add(n);
            }
            Collections.sort(nd, (t1, t2) -> {
                return Boolean.compare(t1.finished, t2.finished);
            });
            NoteDiffUtil dif = new NoteDiffUtil(adapter.getList(), nd);
            DiffUtil.DiffResult d = DiffUtil.calculateDiff(dif);
            synchronized (adapter) {
                adapter.setList(nd);
                d.dispatchUpdatesTo(adapter);
            }
        });


        filterButton.setOnClickListener(l -> {
            sortByTime.setVisibility(state);
            sortByType.setVisibility(state);
            sortByTitle.setVisibility(state);

            if (state == View.GONE) {
                state = View.VISIBLE;
                filterButton.setImageResource(R.drawable.filter_out);
            } else {
                state = View.GONE;
                filterButton.setImageResource(R.drawable.filter);
            }
        });

        sortByType.setOnClickListener(l -> {
            sortByType.setBackgroundResource(R.drawable.sort_square);
            sortByTime.setBackgroundResource(R.drawable.square);
            sortByTitle.setBackgroundResource(R.drawable.square);
            List<NoteDataItem> list = adapter.getList();
            Collections.sort(list, (t1, t2) -> {
                return Integer.compare(t2.type, t1.type);
            });
            Collections.sort(list, (t1, t2) -> {
                return Boolean.compare(t1.finished, t2.finished);
            });
            adapter.setList(list);
        });

        sortByTime.setOnClickListener(l -> {
            sortByType.setBackgroundResource(R.drawable.square);
            sortByTime.setBackgroundResource(R.drawable.sort_square);
            sortByTitle.setBackgroundResource(R.drawable.square);
            List<NoteDataItem> list = adapter.getList();
            Collections.sort(list, (t1, t2) -> {
                return Long.compare(t1.beginDateMls, t2.beginDateMls);
            });
            Collections.sort(list, (t1, t2) -> {
                return Boolean.compare(t1.finished, t2.finished);
            });
            adapter.setList(list);
        });

        sortByTitle.setOnClickListener(l -> {
            sortByType.setBackgroundResource(R.drawable.square);
            sortByTitle.setBackgroundResource(R.drawable.sort_square);
            sortByTime.setBackgroundResource(R.drawable.square);
            List<NoteDataItem> list = adapter.getList();
            Collections.sort(list, (t1, t2) -> {
                return (t1.title.compareTo(t2.title));
            });
            Collections.sort(list, (t1, t2) -> {
                return Boolean.compare(t1.finished, t2.finished);
            });
            adapter.setList(list);
        });


        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(v -> {
            MakeGoals fragment = new MakeGoals(adapter.getList());
            fragment.show(requireActivity().getSupportFragmentManager(), "makeGoal");
        });



    }
}