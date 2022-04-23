package ru.tanec.sdaily.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import ru.tanec.sdaily.R;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.tanec.sdaily.adapters.items.NoteDataItem;
import ru.tanec.sdaily.database.DataBase;
import ru.tanec.sdaily.database.DataBaseApl;
import ru.tanec.sdaily.database.NoteDao;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalViewHolder> {

    List<NoteDataItem> list;
    public Context context;
    FragmentActivity activity;
    int cnt;

    public GoalAdapter(Context context, FragmentActivity activity, List<NoteDataItem> data) {
        this.context = context;
        this.list = data;
        this.activity = activity;
        cnt = -1;
    }

    public void setList(List<NoteDataItem> list) {
        this.list = list;
        cnt = -1;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.note_item;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        cnt++;

        return new GoalViewHolder(view, list.get(cnt), this);

    }

    public void removeItem(NoteDataItem item) {
        list.remove(item);
        notifyDataSetChanged();
        new Thread(() -> {
            DataBase db = DataBaseApl.instance.getDatabase();
            NoteDao dao = db.noteDao();
            dao.delete(dao.getById(item.id));
        }).start();
    }

    @Override
    public void onBindViewHolder(@NonNull GoalViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<NoteDataItem> getList() {
        return list;
    }

    static class GoalViewHolder extends RecyclerView.ViewHolder {

        boolean opened;
        NoteDataItem obj;
        TextView title;
        TextView description;
        ImageView type;
        CardView card;
        TextView time;



        public GoalViewHolder(@NonNull View itemView, NoteDataItem it, GoalAdapter adapter) {
            super(itemView);

            opened = false;

            description = itemView.findViewById(R.id.description);
            time = itemView.findViewById(R.id.time);
            obj = it;
            title = itemView.findViewById(R.id.title);
            title.setText(obj.title);
            type = itemView.findViewById(R.id.type);


            description.setText(obj.getDescriptionSmall());

            if (it.type == 0) {
                type.setImageResource(R.drawable.type0);
            } else if (it.type == 1) {
                type.setImageResource(R.drawable.type1);
            } else {
                type.setImageResource(R.drawable.type2);
            }

            type.setOnClickListener(l -> {
                adapter.removeItem(obj);
            });

            description.setOnClickListener(l -> {
                if (opened) {
                    description.setText(obj.getDescriptionSmall());
                    opened = false;

                } else {
                    description.setText(obj.description);
                    opened = true;
                }
            });
            time.setText(obj.getTime());


        }

        public void bind(NoteDataItem a) {
            description.setText(a.description);
            if (a.type == 0) {
                type.setImageResource(R.drawable.type0);
            } else if (a.type == 1) {
                type.setImageResource(R.drawable.type1);
            } else {
                type.setImageResource(R.drawable.type2);
            }
            title.setText(a.title);
            time.setText(a.getTime());

        }

    }
}







