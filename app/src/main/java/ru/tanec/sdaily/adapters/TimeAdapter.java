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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public void addDataItem(TimeTableItem item) {
        list.add(item);
        notifyItemInserted(list.size() - 1);
    }

    public void changeDataItem(TimeTableItem item, int id) {
        if (id > list.size() - 1) {
            list.add(item);
            notifyItemInserted(list.size() - 1);
        } else {
            list.remove(id);
            list.add(id, item);
            notifyItemChanged(id);
        }
        cnt = -1;
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
    public int getItemCount() {
        return list.size();
    }

    static class TimeViewHolder extends RecyclerView.ViewHolder {
        TimeTableItem obj;
        TextView title;
        RecyclerView recycler;
        RecyclerView ranges_recycler;
        ImageView collapse_button;
        int HEIGHT_VISIBLE = 1;
        int HEIGHT_HIDDEN = -1;
        int height = HEIGHT_HIDDEN;
        ArrayList<String[]> list = new ArrayList<>();
        RangesAdapter rAdapter = new RangesAdapter(list);
        HashMap<String, Integer> resourceDay = new HashMap<>();

        public TimeViewHolder(@NonNull View itemView, TimeTableItem it, Context context) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title);
            this.obj = it;
            setResourceDay();
            this.title.setText(resourceDay.get(it.title));
            ranges_recycler = itemView.findViewById(R.id.ranges_recycler);
            ranges_recycler.setAdapter(rAdapter);
            collapse_button = itemView.findViewById(R.id.collapse_button);
            collapse_button.animate().scaleY(-1f);
            collapse_button.setOnClickListener(l -> {
                if (height == HEIGHT_HIDDEN) {
                    ranges_recycler.setVisibility(View.VISIBLE);
                    new Thread(() -> {

                        DataBase db = DataBaseApl.instance.getDatabase();
                        TimeTableDao td = db.timeTableDao();
                        RangeItem[] a;
                        ArrayList<String[]> list = new ArrayList<>();
                        a = td.getByTitle(obj.title).timerange;

                        for (RangeItem rangeItem : a) {
                            if (rangeItem != null) {
                                list.add(rangeItem.toData());
                            }
                        }

                        new Handler(Looper.getMainLooper()).post(() -> {
                            rAdapter.setList(list);
                        });
                    }).start();
                    height = HEIGHT_VISIBLE;
                } else {
                    ranges_recycler.setVisibility(View.GONE);
                    height = HEIGHT_HIDDEN;
                }
                collapse_button.animate().scaleY(height);
            });

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

        void bind(TimeTableItem item, FragmentActivity activity, int position) {
            title.setText(resourceDay.get(item.title));
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
        holder.bind(list.get(position), activity, position);
    }

}
