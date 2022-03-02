package ru.tanec.sdaily.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import ru.tanec.sdaily.R;
import ru.tanec.sdaily.adapters.TaskAdapter;
import ru.tanec.sdaily.adapters.items.NoteDataItem;

public class Task extends Fragment {
    FragmentActivity activity;
    RecyclerView goalrecycler;
    Context context;

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

        NoteDataItem[] data = {null, null, null, null, null, null, null, null, null};
        goalrecycler.setAdapter(new TaskAdapter(context, activity, data));

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}