package ru.tanec.sdaily.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import ru.tanec.sdaily.R;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {
    Context context;
    Boolean[] list;
    int cnt;

    public DayAdapter(Context context, Boolean[] list){
        this.list = list;
        this.context = context;
        this.cnt = -1;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.day_item;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        cnt++;

        return new DayViewHolder(view, list, cnt, context);

    }

    @Override
    public void onBindViewHolder(@NonNull DayAdapter.DayViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 24;
    }

    static class DayViewHolder extends RecyclerView.ViewHolder {

        ImageView dayimage;

        @SuppressLint("ResourceAsColor")
        public DayViewHolder(@NonNull View itemView, Boolean[] dl, int id, Context context) {
            super(itemView);
            this.dayimage = itemView.findViewById(R.id.day_image_rec);
            if (dl[id] == null) {
                dl[id] = false;
            }
            if (dl[id] == true) {
                ImageViewCompat.setImageTintList(dayimage, ColorStateList.valueOf(ContextCompat.getColor(context, R.color.md_theme_light_onSurface)));
            } else {
                ImageViewCompat.setImageTintList(dayimage, ColorStateList.valueOf(ContextCompat.getColor(context, R.color.md_theme_dark_onSurfaceVariant)));
            }
        }
    }
}
