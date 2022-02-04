package ru.tanec.sdaily;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class Goal extends Fragment {

    Context context;

    public Goal() {
        super(R.layout.fragment_goal);
    }

    public Goal(Context context) {
        super(R.layout.fragment_goal);
        this.context = context;
    }

    RecyclerView noteRecycler;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*noteRecycler = view.findViewById(R.id.goal_recycler);
        NoteDataItem[] data = null;
        noteRecycler.setAdapter(new NoteAdapter(context, data));*/

    }
}