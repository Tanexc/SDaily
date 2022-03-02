package ru.tanec.sdaily;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
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

import java.util.Map;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeViewHolder> {

    TimeTableItem[] list;
    public Context context;
    FragmentActivity activity;
    String[] id_title;
    int cnt;

    public TimeAdapter(Context context, FragmentActivity activity, TimeTableItem[] list, String[] id_title) {
        this.context = context;
        this.list = list;
        this.activity = activity;
        this.id_title = id_title;
        cnt = -1;
    }

    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.time_list_item;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        cnt++;

        return new TimeViewHolder(view, list[cnt]);

    }
   // a



    @Override
    public int getItemCount() {
        return list.length;
    }

    static class TimeViewHolder extends RecyclerView.ViewHolder {

        TimeTableItem obj;
        TextView title;
        RecyclerView recycler;

        public TimeViewHolder(@NonNull View itemView, TimeTableItem it) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title);
            this.obj = it;
            this.title.setText(it.title);
        }

        public void dayFill() {
            recycler = itemView.findViewById(R.id.day_recycler);
            recycler.setAdapter(new DayAdapter(itemView.getContext(), obj.fill));
        }

        void bind(TimeTableItem item, FragmentActivity activity, int position) {
            title.setText(item.title);
            obj = item;
            dayFill();
            itemView.setOnClickListener(v -> {
                RangeFragment fragment = new RangeFragment(obj);
                fragment.show(activity.getSupportFragmentManager(), "asd");
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull TimeViewHolder holder, int position) {
        holder.bind(list[position], activity, position);
    }

}
