package ru.tanec.sdaily.adapters;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import ru.tanec.sdaily.R;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.tanec.sdaily.adapters.items.NoteDataItem;
import ru.tanec.sdaily.database.DataBase;
import ru.tanec.sdaily.database.DataBaseApl;
import ru.tanec.sdaily.database.NoteDao;
import ru.tanec.sdaily.database.NoteEntity;
import ru.tanec.sdaily.interfaces.ItemTouchHelperAdapter;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalViewHolder> implements ItemTouchHelperAdapter {

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

        return new GoalViewHolder(view, list.get(cnt));

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

    @Override
    public void onItemDismiss(int position) {
        NoteDataItem c = list.get(position);
        int id = (int) c.id;
        DataBase db = DataBaseApl.instance.getDatabase();
        new Thread(() -> {
            db.noteDao().delete(db.noteDao().getById(c.id));
        }).start();
        list.remove(c);
        setList(list);
        notifyItemChanged(id - 1);
    }

    static public class GoalViewHolder extends RecyclerView.ViewHolder {

        boolean opened;
        NoteDataItem obj;
        TextView title;
        TextView description;
        ImageView type;
        TextView time;
        public ConstraintLayout layout;

        DataBase db = DataBaseApl.instance.getDatabase();



        public GoalViewHolder(@NonNull View itemView, NoteDataItem it) {
            super(itemView);

            opened = false;

            description = itemView.findViewById(R.id.description);
            time = itemView.findViewById(R.id.time);
            obj = it;
            title = itemView.findViewById(R.id.title);
            title.setText(obj.title);
            type = itemView.findViewById(R.id.type);
            layout = itemView.findViewById(R.id.cardLayout);

            if (obj.finished) {
                layout.setBackgroundResource(R.color.light_gray);
            }

            description.setText(obj.getDescriptionSmall());

            if (obj.type == 0) {
                type.setImageResource(R.drawable.type0);
            } else if (obj.type == 1) {
                type.setImageResource(R.drawable.type1);
            } else {
                type.setImageResource(R.drawable.type2);
            }

            type.setOnClickListener(l -> {
                if (!obj.finished) {
                    layout.setBackgroundResource(R.color.light_gray);
                    obj.finished = true;
                } else {
                    layout.setBackgroundResource(R.color.white);
                    obj.finished = false;
                }
                NoteDao nd = db.noteDao();

                new Thread(() -> {
                    NoteEntity noteEntity = nd.getById(obj.id);
                    noteEntity.finished = obj.finished;
                    nd.update(noteEntity);
                }).start();

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
            obj = a;
            description.setText(a.getDescriptionSmall());
            if (a.type == 0) {
                type.setImageResource(R.drawable.type0);
            } else if (a.type == 1) {
                type.setImageResource(R.drawable.type1);
            } else {
                type.setImageResource(R.drawable.type2);
            }
            title.setText(a.title);
            time.setText(a.getTime());
            if (obj.finished) {
                layout.setBackgroundResource(R.color.light_gray);
            } else {
                layout.setBackgroundResource(R.color.white);
            }

        }

    }
}







