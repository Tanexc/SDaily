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

        MTimePicker timePicker;
        Button plus_btn;

        public DialogViewHolder(@NonNull View itemView, DialogItem[] list, int id) {
            super(itemView);
            this.timePicker = itemView.findViewById(R.id.timePicker);
            this.plus_btn = itemView.findViewById(R.id.plus_btn);
            timePicker.setMinute(10);
            plus_btn.setOnClickListener(v -> {
                timePicker.plusMinute();
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
