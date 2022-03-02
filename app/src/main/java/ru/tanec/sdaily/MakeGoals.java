package ru.tanec.sdaily;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class MakeGoals extends DialogFragment {

    public MakeGoals() {
        super(R.layout.fragment_makegoal);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = requireDialog();
        dialog.getWindow().setLayout(MATCH_PARENT, MATCH_PARENT);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.Theme_SDaily);
    }
}
