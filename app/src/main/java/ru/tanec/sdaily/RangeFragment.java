package ru.tanec.sdaily;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class RangeFragment extends DialogFragment {

    RecyclerView dialog_recycler;

    public RangeFragment() {
        super(R.layout.fragment_range);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_range, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dialog_recycler = view.findViewById(R.id.time_dialog_recycler);
        DialogItem[] data = new DialogItem[5];
        for (int i = 0; i < 5; i++) {
            data[i] = new DialogItem();
        }
        dialog_recycler.setAdapter(new DialogAdapter(requireContext(), data));
    }
}