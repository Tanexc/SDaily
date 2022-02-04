package ru.tanec.sdaily;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public Context context;
    public ItemList[] list;

    public BaseAdapter(Context context, ItemList[] list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemCount() {
        try {
            return list.length;
        } catch (Exception e) {
            return 5;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    }

}
