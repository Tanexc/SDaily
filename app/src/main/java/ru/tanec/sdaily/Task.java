package ru.tanec.sdaily;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class Task extends Fragment {

    RecyclerView noteRecycler;
    Context context;

    public Task() {
        super(R.layout.fragment_task);
    }

    public Task(Context context) {
        super(R.layout.fragment_task);
        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*noteRecycler = view.findViewById(R.id.task_recycler);
        NoteDataItem[] data = null;
        noteRecycler.setAdapter(new NoteAdapter(getActivity().getApplicationContext(), data));*/
    }
}