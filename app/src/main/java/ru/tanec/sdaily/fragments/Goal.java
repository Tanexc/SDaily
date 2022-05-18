package ru.tanec.sdaily.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yariksoffice.lingver.Lingver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.tanec.sdaily.R;
import ru.tanec.sdaily.activity.MainActivity;
import ru.tanec.sdaily.adapters.GoalAdapter;
import ru.tanec.sdaily.adapters.items.NoteDataItem;
import ru.tanec.sdaily.custom.StaticValues;
import ru.tanec.sdaily.database.DataBase;
import ru.tanec.sdaily.database.DataBaseApl;
import ru.tanec.sdaily.database.NoteDao;
import ru.tanec.sdaily.database.NoteEntity;
import ru.tanec.sdaily.helpers.NoteDiffUtil;
import ru.tanec.sdaily.helpers.SimpleItemTouchHelperCallback;

public class Goal extends Fragment {

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

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(goalrecycler);

        StaticValues.liveDate.observe(getViewLifecycleOwner(), date -> {
            new Thread(() -> {
                List<NoteDataItem> nd = new ArrayList<NoteDataItem>();
                NoteEntity[] noteEntities = db.noteDao().getByDate(date.getTime());
                for (NoteEntity note: noteEntities) {
                    NoteDataItem n = new NoteDataItem();
                    n.setFromEntity(note);
                    nd.add(n);
                }
                Collections.sort(nd, (t1, t2) -> {
                    return Boolean.compare(t1.finished, t2.finished);
                });
                new Handler(Looper.getMainLooper()).post(() -> {
                    synchronized (adapter) {
                        adapter.setList(nd);
                    }

                });
            }).start();

        });

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
            MakeGoals fragment = new MakeGoals();
            fragment.show(requireActivity().getSupportFragmentManager(), "makeGoal");
        });



    }
}