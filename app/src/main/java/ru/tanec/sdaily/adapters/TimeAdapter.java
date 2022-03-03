package ru.tanec.sdaily.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import ru.tanec.sdaily.R;
import ru.tanec.sdaily.fragments.RangeFragment;
import ru.tanec.sdaily.adapters.items.TimeTableItem;

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
   // abcc



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
