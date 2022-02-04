package ru.tanec.sdaily;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class TimeTable extends Fragment {

    FragmentActivity activity;
    Context context;
    RecyclerView timetableRecycler;

    public TimeTable() {
        super(R.layout.fragment_timetable);
    }

    public TimeTable(Context context) {
        super(R.layout.fragment_timetable);
        this.context = requireContext();
        this.activity = requireActivity();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        timetableRecycler = view.findViewById(R.id.timetable_recycler);
        TimeTableItem[] data = new TimeTableItem[7];

        for (int i = 0; i < 7; i++) {
            data[i] = new TimeTableItem("Понедельник");
            data[i].setFill(new Boolean[]{true, true, true, true, false, false, false, true, true, false, false, false, true, true, true, true, false, false, false, true, true, false, false, false, true});
        }
        Context c = getActivity().getApplicationContext();
        timetableRecycler.setAdapter(new TimeAdapter(requireContext(), requireActivity(), data));
    }
}