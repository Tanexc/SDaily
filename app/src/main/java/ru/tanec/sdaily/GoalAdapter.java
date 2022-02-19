package ru.tanec.sdaily;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalViewHolder> {

    NoteDataItem[] list;
    public Context context;
    FragmentActivity activity;
    int cnt;

    public GoalAdapter(Context context, FragmentActivity activity, NoteDataItem[] data) {
        this.context = context;
        this.list = data;
        this.activity = activity;
        cnt = -1;
    }


    @NonNull
    @Override
    public GoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.note_item;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        cnt++;

        return new GoalViewHolder(view, list[cnt]);

    }

    @Override
    public void onBindViewHolder(@NonNull GoalViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    static class GoalViewHolder extends RecyclerView.ViewHolder {

        NoteDataItem obj;
        TextView title;


        public GoalViewHolder(@NonNull View itemView, NoteDataItem it) {
            super(itemView);
            /*this.title = itemView.findViewById(R.id.title);
            this.obj = it;
            this.title.setText(it.title);*/
        }

    }
}







