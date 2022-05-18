package ru.tanec.sdaily.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import ru.tanec.sdaily.custom.HTimePicker;
import ru.tanec.sdaily.custom.MTimePicker;
import ru.tanec.sdaily.R;
import ru.tanec.sdaily.adapters.items.RangeItem;

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

        return new DialogViewHolder(view, list, id, this);
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
        HTimePicker start_hour;
        HTimePicker end_hour;
        MTimePicker start_minute;
        MTimePicker end_minute;
        Button plus_s_minute;
        Button plus_e_minute;
        Button minus_s_minute;
        Button minus_e_minute;
        Button plus_s_hour;
        Button plus_e_hour;
        Button minus_s_hour;
        Button minus_e_hour;

        public DialogViewHolder(@NonNull View itemView, RangeItem[] list, int id, DialogAdapter adpt) {
            super(itemView);
            this.start_minute = itemView.findViewById(R.id.start_minute);
            this.plus_s_minute = itemView.findViewById(R.id.plus_s_minute);
            this.minus_s_minute = itemView.findViewById(R.id.minus_s_minute);
            this.start_hour = itemView.findViewById(R.id.start_hour);
            this.plus_s_hour = itemView.findViewById(R.id.plus_s_hour);
            this.minus_s_hour = itemView.findViewById(R.id.minus_s_hour);
            this.end_minute = itemView.findViewById(R.id.end_minute);
            this.plus_e_minute = itemView.findViewById(R.id.plus_e_minute);
            this.minus_e_minute = itemView.findViewById(R.id.minus_e_minute);
            this.end_hour = itemView.findViewById(R.id.end_hour);
            this.plus_e_hour = itemView.findViewById(R.id.plus_e_hour);
            this.minus_e_hour = itemView.findViewById(R.id.minus_e_hour);
            this.title = itemView.findViewById(R.id.textInputLayout);
            this.adpt = adpt;
            this.delete_btn = itemView.findViewById(R.id.delete_btn);

            int[] s_hm;
            int[] e_hm;
            if (list[id] != null) {
                s_hm = list[id].getStartTime();
                e_hm = list[id].getEndTime();
            } else {
                s_hm = new int[]{0, 0};
                e_hm = new int[]{0, 0};
            }

            start_minute.setMinute(s_hm[1]);
            start_hour.setHour(s_hm[0]);
            end_minute.setMinute(e_hm[1]);
            end_hour.setHour(e_hm[0]);

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

            plus_s_minute.setOnClickListener(v -> {
                start_minute.plusMinute();
                list[id].setStartTime(start_hour.getHour(), start_minute.getMinute());
                adpt.list[id] = list[id];
            });
            minus_s_minute.setOnClickListener(v -> {
                start_minute.minusMinute();
                list[id].setStartTime(start_hour.getHour(), start_minute.getMinute());
                adpt.list[id] = list[id];
            });
            plus_s_hour.setOnClickListener(v -> {
                start_hour.plusHour();
                list[id].setStartTime(start_hour.getHour(), start_minute.getMinute());
                adpt.list[id] = list[id];
            });
            minus_s_hour.setOnClickListener(v -> {
                start_hour.minusHour();
                list[id].setStartTime(start_hour.getHour(), start_minute.getMinute());
                adpt.list[id] = list[id];
            });
            plus_e_minute.setOnClickListener(v -> {
                end_minute.plusMinute();
                list[id].setEndTime(end_hour.getHour(), end_minute.getMinute());
                adpt.list[id] = list[id];
            });
            minus_e_minute.setOnClickListener(v -> {
                end_minute.minusMinute();
                list[id].setEndTime(end_hour.getHour(), end_minute.getMinute());
                adpt.list[id] = list[id];
            });
            plus_e_hour.setOnClickListener(v -> {
                end_hour.plusHour();
                list[id].setEndTime(end_hour.getHour(), end_minute.getMinute());
                adpt.list[id] = list[id];
            });
            minus_e_hour.setOnClickListener(v -> {
                end_hour.minusHour();
                list[id].setEndTime(end_hour.getHour(), end_minute.getMinute());
                adpt.list[id] = list[id];
            });
        }

        void bind(RangeItem item) {
            int[] s_hm;
            int[] e_hm;
            if (item != null) {
                s_hm = item.getStartTime();
                e_hm = item.getEndTime();
            } else {
                s_hm = new int[]{0, 0};
                e_hm = new int[]{0, 0};
            }
            if (item.title != null) {
                if(!item.title.equals("u$n$t$i$t$l$e$d")) {
                    title.setText((CharSequence)  item.title);
                }
            }
            start_hour.setHour(s_hm[0]);
            end_hour.setHour(e_hm[0]);
            start_minute.setMinute(s_hm[1]);
            end_minute.setMinute(e_hm[1]);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull DialogViewHolder holder, int position) {
        holder.bind(list[position]);
    }
}
