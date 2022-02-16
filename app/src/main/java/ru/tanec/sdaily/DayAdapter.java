package ru.tanec.sdaily;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        int layoutIdForListItem = R.layout.fragment_goal;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        cnt++;

        return new DayViewHolder(view, list, cnt);

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

        public DayViewHolder(@NonNull View itemView, Boolean[] dl, int id) {
            super(itemView);
            this.dayimage = itemView.findViewById(R.id.day_image_rec);
            if (dl[id] == null) {
                dl[id] = false;
            }
            if (dl[id]) {
                this.dayimage.setBackgroundResource(R.drawable.square);
            } else {
                this.dayimage.setBackgroundResource(R.drawable.white_square);
            }
        }
    }
}
