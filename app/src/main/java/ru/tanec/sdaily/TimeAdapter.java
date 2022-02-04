package ru.tanec.sdaily;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeViewHolder> {

    TimeTableItem[] list;
    public Context context;
    FragmentActivity activity;

    public TimeAdapter(Context context, FragmentActivity activity, TimeTableItem[] list) {
        this.context = context;
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.time_list_item;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        Boolean[] fill = {true, true, true, true, false, false, false, true, true, false, false, false, true, true, true, true, false, false, false, true, true, false, false, false, true};

        return new TimeViewHolder(view, fill);

    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    static class TimeViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        Boolean[] fill;
        ImageButton open_btn;
        ConstraintLayout expandable;
        RecyclerView recycler;

        public TimeViewHolder(@NonNull View itemView, Boolean[] fill) {
            super(itemView);
            this.open_btn = itemView.findViewById(R.id.timetable_item_btn);
            this.title = itemView.findViewById(R.id.title);
            this.fill = fill;
            this.expandable = itemView.findViewById(R.id.expandable_card);
        }

        public void dayFill() {
            recycler = itemView.findViewById(R.id.day_recycler);
            recycler.setAdapter(new DayAdapter(itemView.getContext(), fill));
        }

        void bind(TimeTableItem item, FragmentActivity activity) {
            title.setText(item.title);
            fill = item.fill;
            dayFill();
            itemView.setOnClickListener(v -> {
                DialogFragment fragment = new RangeFragment();
                fragment.show(activity.getSupportFragmentManager(), "asd");
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull TimeViewHolder holder, int position) {
        holder.bind(list[position], activity);
    }

}
