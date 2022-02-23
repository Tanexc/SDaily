package ru.tanec.sdaily;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class MakeGoals extends Fragment{
    FragmentActivity activity;
    Context context;

    public MakeGoals() {
        super(R.layout.fragment_makegoal);
        this.context = requireContext();
        this.activity = requireActivity();

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity.setContentView(R.layout.fragment_makegoal);
    }

}
