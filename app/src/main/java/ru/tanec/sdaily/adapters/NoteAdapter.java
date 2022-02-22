package ru.tanec.sdaily.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.tanec.sdaily.NoteDataItem;
import ru.tanec.sdaily.R;

public class NoteAdapter extends BaseAdapter {

    Context context;

    public NoteAdapter(Context context, NoteDataItem[] list){
        super(context, list);
        this.context = context;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.note_item;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new NoteViewHolder(view);
        
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {


        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

}
