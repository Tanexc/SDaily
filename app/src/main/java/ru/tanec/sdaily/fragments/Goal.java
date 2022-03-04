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

import java.util.Date;

public class Goal extends Fragment {

    FragmentActivity activity;
    Context context;
    RecyclerView goalrecycler;
    DataBase db = DataBaseApl.instance.getDatabase();

    public Goal() {
        super(R.layout.fragment_goal);
    }

    public Goal(Context context) {
        super(R.layout.fragment_goal);
        this.context = requireContext();
        this.activity = requireActivity();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        goalrecycler = view.findViewById(R.id.goal_recycler);

        StaticValues.liveDate.observe(getViewLifecycleOwner(), date -> {
            new Thread(() -> {
                NoteDao nd = db.noteDao();
                NoteEntity[] primaryData = nd.getByDate(StaticValues.viewDate.getTime());
                NoteDataItem[] data = new NoteDataItem[primaryData.length];
                for (int i = 0; i < primaryData.length; i++) {
                    data[i] = new NoteDataItem();
                    data[i].setFromEntity(primaryData[i]);
                }
                new Handler(Looper.getMainLooper()).post(() -> {
                    goalrecycler.setAdapter(new GoalAdapter(context, activity, data));
                });
            }).start();
        });


        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(v -> {
            MakeGoals fragment = new MakeGoals();
            fragment.show(requireActivity().getSupportFragmentManager(), "makeGoal");
        });
    }
}

