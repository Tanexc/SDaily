package ru.tanec.sdaily.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.tanec.sdaily.R;

public class RangesAdapter extends RecyclerView.Adapter<RangesAdapter.RangesViewHolder> {

    ArrayList<String[]> list;
    int cnt;

    public RangesAdapter(ArrayList<String[]> list) {
        this.list = list;
        this.cnt = -1;
    }

    @NonNull
    @Override
    public RangesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.range_item;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        cnt++;

        return new RangesViewHolder(view, list.get(cnt));

    }

    @Override
    public void onBindViewHolder(@NonNull RangesViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(ArrayList<String[]> list) {
        this.list = list;
        cnt = -1;
        notifyDataSetChanged();
    }

    static public class RangesViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView duration;

        public RangesViewHolder(@NonNull View itemView, String[] info) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            duration = itemView.findViewById(R.id.duration);
            if (!info[0].equals("u$n$t$i$t$l$e$d")) {
                title.setText(info[0]);
            } else {
                title.setText(R.string.untitled);
            }
            duration.setText(info[1]);
        }

        public void bind(String[] info) {
            title.setText(info[0]);
            if (!info[0].equals("u$n$t$i$t$l$e$d")) {
                title.setText(info[0]);
            } else {
                title.setText(R.string.untitled);
            }
            duration.setText(info[1]);
        }



}
}
