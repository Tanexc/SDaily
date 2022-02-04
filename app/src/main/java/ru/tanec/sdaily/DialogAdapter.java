package ru.tanec.sdaily;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.DialogViewHolder> {

    DialogItem[] list;
    Context context;
    int id;

    public DialogAdapter(Context context, DialogItem[] list) {
        this.context = context;
        this.list = list;
        this.id = -1;
    }

    @NonNull
    @Override
    public DialogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.dialog_item_layout;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        id++;

        return new DialogViewHolder(view, list, id);
    }


    public int getItemCount() {
        return list.length;
    }

    static class DialogViewHolder extends RecyclerView.ViewHolder {

        TextInputEditText title;
        MTimePicker start_minute;
        MTimePicker end_minute;
        Button plus_s_minute;
        Button plus_e_minute;
        Button minus_s_minute;
        Button minus_e_minute;
        Button plus_s_hour;
        Button plus_e_hour;
        Button minus_s_hour;
        Button minus_e_hour;

        public DialogViewHolder(@NonNull View itemView, DialogItem[] list, int id) {
            super(itemView);
            this.start_minute = itemView.findViewById(R.id.start_minute);
            this.plus_s_minute = itemView.findViewById(R.id.plus_s_minute);
            this.minus_s_minute = itemView.findViewById(R.id.minus_s_minute);
            /*Integer[] s_hm;
            Integer[] e_hm;
            s_hm = list[id].getStartTime();
            e_hm = list[id].getStartTime();*/
            start_minute.setMinute(10);
            plus_s_minute.setOnClickListener(v -> {
                start_minute.plusMinute();
            });
            minus_s_minute.setOnClickListener(v -> {
                start_minute.minusMinute();
            });


        }

        void bind(DialogItem item) {

        }
    }

    @Override
    public void onBindViewHolder(@NonNull DialogViewHolder holder, int position) {
        holder.bind(list[position]);
    }


}
