package ru.tanec.sdaily.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import ru.tanec.sdaily.adapters.items.NoteDataItem;
import ru.tanec.sdaily.R;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    NoteDataItem[] list;
    public Context context;
    FragmentActivity activity;
    int cnt;

    public TaskAdapter(Context context, FragmentActivity activity, NoteDataItem[] data) {
        this.context = context;
        this.list = data;
        this.activity = activity;
        cnt = -1;
    }

    @NonNull
    @Override
    public TaskAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.note_item;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        cnt++;

        return new TaskAdapter.TaskViewHolder(view, list[cnt]);

    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {

        NoteDataItem obj;
        TextView title;


        public TaskViewHolder(@NonNull View itemView, NoteDataItem it) {
            super(itemView);
            /*this.title = itemView.findViewById(R.id.title);
            this.obj = it;
            this.title.setText(it.title);*/
        }

    }
}
