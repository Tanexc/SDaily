package ru.tanec.sdaily;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class Goal extends Fragment {

    FragmentActivity activity;
    Context context;
    RecyclerView goalrecycler;


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

        NoteDataItem[] data = {null, null, null, null, null, null, null, null, null};
        goalrecycler.setAdapter(new GoalAdapter(context, activity, data));

    }
}