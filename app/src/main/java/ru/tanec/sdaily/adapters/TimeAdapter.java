package ru.tanec.sdaily.adapters;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import ru.tanec.sdaily.R;
import ru.tanec.sdaily.adapters.items.RangeItem;
import ru.tanec.sdaily.database.DataBase;
import ru.tanec.sdaily.database.DataBaseApl;
import ru.tanec.sdaily.database.TimeTableDao;
import ru.tanec.sdaily.database.TimeTableEntity;
import ru.tanec.sdaily.fragments.RangeFragment;
import ru.tanec.sdaily.adapters.items.TimeTableItem;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeViewHolder> {

    ArrayList<TimeTableItem> list;
    public Context context;
    FragmentActivity activity;
    int cnt;

    public TimeAdapter(Context context, FragmentActivity activity, ArrayList<TimeTableItem> list) {
        this.context = context;
        this.list = list;
        this.activity = activity;
        cnt = -1;
    }

    public void setData(ArrayList<TimeTableItem> data) {
        list = data;
        cnt = -1;
        notifyDataSetChanged();
    }

    public void changeDataItem(TimeTableItem item, int id) {
        if (id > list.size() - 1) {
            list.add(item);
        } else {
            list.remove(id);
            list.add(id, item);
        }
        cnt = -1;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.time_list_item;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        cnt++;

        return new TimeViewHolder(view, list.get(cnt), context);
    }


    @Override
    public void onBindViewHolder(@NonNull TimeViewHolder holder, int position) {
        holder.bind(list.get(position), activity);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    static class TimeViewHolder extends RecyclerView.ViewHolder {
        TimeTableItem obj;
        TextView title;
        RecyclerView recycler;
        TextView alarmText;
        RecyclerView ranges_recycler;
        Context context;
        ImageView collapse_button;

        int HEIGHT_VISIBLE = 1;
        int HEIGHT_HIDDEN = -1;
        int height = HEIGHT_HIDDEN;

        RangesAdapter rAdapter;
        HashMap<String, Integer> resourceDay = new HashMap<>();


        public TimeViewHolder(@NonNull View itemView, TimeTableItem it, Context context) {
            super(itemView);
            setResourceDay();

            this.title = itemView.findViewById(R.id.title);
            this.obj = it;
            this.context = context;
            this.title.setText(resourceDay.get(it.title));
            ArrayList<String[]> w = obj.getStringRanges();

            rAdapter = new RangesAdapter(w);

            ranges_recycler = itemView.findViewById(R.id.ranges_recycler);
            ranges_recycler.setAdapter(rAdapter);
            collapse_button = itemView.findViewById(R.id.collapse_button);
            collapse_button.animate().scaleY(-1f);
            alarmText = itemView.findViewById(R.id.alarm_text);

            if (obj.intersects()) {
                alarmText.setVisibility(View.VISIBLE);
            } else {
                alarmText.setVisibility(View.INVISIBLE);
            }

            collapse_button.setOnClickListener(l -> {
                checkVisibility();
            });
            dayFill();
        }

        private void checkVisibility() {
            if (height == HEIGHT_HIDDEN) {
                ranges_recycler.setVisibility(View.VISIBLE);
                height = HEIGHT_VISIBLE;
            } else {
                height = HEIGHT_HIDDEN;
                ranges_recycler.setVisibility(View.GONE);
            }
            collapse_button.animate().scaleY(height);
        }

        void bind(TimeTableItem item, FragmentActivity activity) {
            title.setText(resourceDay.get(item.title));
            obj = item;

            rAdapter.setList(obj.getStringRanges());

            if (obj.intersects()) {
                alarmText.setVisibility(View.VISIBLE);
            } else {
                alarmText.setVisibility(View.INVISIBLE);
            }

            dayFill();
            itemView.setOnClickListener(v -> {
                RangeFragment fragment = new RangeFragment(obj);
                fragment.show(activity.getSupportFragmentManager(), "asd");
            });

            ranges_recycler.setAdapter(rAdapter);
        }

        private void setResourceDay() {
            resourceDay.put("Monday", R.string.monday);
            resourceDay.put("Tuesday", R.string.tuesday);
            resourceDay.put("Wednesday", R.string.wednesday);
            resourceDay.put("Thursday", R.string.thursday);
            resourceDay.put("Friday", R.string.friday);
            resourceDay.put("Saturday", R.string.saturday);
            resourceDay.put("Sunday", R.string.sunday);
        }

        public void dayFill() {
            recycler = itemView.findViewById(R.id.day_recycler);
            recycler.setAdapter(new DayAdapter(itemView.getContext(), obj.fill));
        }
    }


}
