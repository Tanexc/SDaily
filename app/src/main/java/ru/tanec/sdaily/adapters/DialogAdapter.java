package ru.tanec.sdaily.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import ru.tanec.sdaily.R;
import ru.tanec.sdaily.adapters.items.RangeItem;
import ru.tanec.sdaily.fragments.TimePickDialog;

public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.DialogViewHolder> {

    RangeItem[] list;
    Context context;
    int id;

    public DialogAdapter(Context context, RangeItem[] list) {
        this.context = context;
        this.list = list;
        this.id = -1;
        if (list[0] == null) {
            list[0] = new RangeItem();
        }
    }

    public RangeItem[] getList() {
        return list;
    }

    @NonNull
    @Override
    public DialogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.dialog_item_layout;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        id++;

        return new DialogViewHolder(view, list, id, this, context);
    }


    public int getItemCount() {
        int lg;
        try {
            lg = list.length;
        } catch (Exception e) {
            lg = 0;
        }
        return lg;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void addItem(RangeItem ndi) {
        int d = getItemCount() + 1;
        RangeItem[] list1 = new RangeItem[d];
        if (d != 1) {
            System.arraycopy(list, 0, list1, 0, d - 1);
        }
        list1[d - 1] = ndi;
        list = list1;
        notifyItemInserted(d - 1);
    }

    static class DialogViewHolder extends RecyclerView.ViewHolder {

        ImageView delete_btn;
        DialogAdapter adpt;
        TextInputEditText title;
        TextView startTime;
        TextView endTime;
        Context context;

        public DialogViewHolder(@NonNull View itemView, RangeItem[] list, int id, DialogAdapter adpt, Context context) {
            super(itemView);
            this.startTime = itemView.findViewById(R.id.start_time);
            this.endTime = itemView.findViewById(R.id.end_time);
            this.title = itemView.findViewById(R.id.textInputLayout);
            this.adpt = adpt;
            this.delete_btn = itemView.findViewById(R.id.delete_btn);
            this.context = context;

            startTime.setText(list[id].getStringDuration().split(" - ")[0]);
            endTime.setText(list[id].getStringDuration().split(" - ")[1]);

            startTime.setOnClickListener(l -> {
                TimePickDialog fragment = new TimePickDialog(R.string.dialog_item_begin, list[id].start_hour, list[id].start_minute, startTime);
                fragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "startDialog");

            });

            endTime.setOnClickListener(l -> {
                TimePickDialog fragment = new TimePickDialog(R.string.dialog_item_finish, list[id].end_hour, list[id].end_minute, endTime);
                fragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "endDialog");
            });

            endTime.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String[] time = endTime.getText().toString().split(":");
                    list[id].end_hour = (int) Integer.parseInt(time[0]);
                    list[id].end_minute = (int) Integer.parseInt(time[1]);

                }
            });

            startTime.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String[] time = startTime.getText().toString().split(":");
                    list[id].start_hour = (int) Integer.parseInt(time[0]);
                    list[id].start_minute = (int) Integer.parseInt(time[1]);
                }
            });

            delete_btn.setOnClickListener(v -> {
                list[id].delete();
                int k;
                if (list[id].deleted == -1) {
                    k = 1;
                    delete_btn.setImageResource(R.drawable.open);
                } else {
                    delete_btn.setImageResource(R.drawable.close);
                    k = 0;
                }
                delete_btn.animate().rotation(0f).start();
                delete_btn.animate().rotation((float) (k * 45)).start();
            });

            title.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    list[id].setTitle(title.getText().toString());
                    adpt.list[id] = list[id];
                }
            });


        }

        void bind(RangeItem item) {
            startTime.setOnClickListener(l -> {
                TimePickDialog fragment = new TimePickDialog(R.string.dialog_item_begin, item.start_hour, item.start_minute, startTime);
                fragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "startDialog");

            });

            endTime.setOnClickListener(l -> {
                TimePickDialog fragment = new TimePickDialog(R.string.dialog_item_finish, item.end_hour, item.end_minute, endTime);
                fragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "endDialog");
            });


            startTime.setText(item.getStringDuration().split(" - ")[0]);
            endTime.setText(item.getStringDuration().split(" - ")[1]);

            if (item.title != null) {
                if(!item.title.equals("u$n$t$i$t$l$e$d")) {
                    title.setText((CharSequence)  item.title);
                }
            }

        }
    }

    @Override
    public void onBindViewHolder(@NonNull DialogViewHolder holder, int position) {
        holder.bind(list[position]);
    }
}
