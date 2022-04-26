package ru.tanec.sdaily.helpers;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import ru.tanec.sdaily.adapters.items.NoteDataItem;
import ru.tanec.sdaily.database.NoteEntity;;

public class NoteDiffUtil extends DiffUtil.Callback {

    private final List<NoteDataItem> oldlist;
    private final List<NoteDataItem> newlist;

    public NoteDiffUtil(List<NoteDataItem> oldlist, List<NoteDataItem> newlist) {
        this.oldlist = oldlist;
        this.newlist = newlist;
    }

    @Override
    public int getOldListSize() {
        return oldlist.size();
    }

    @Override
    public int getNewListSize() {
        return newlist.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        NoteDataItem olditem = oldlist.get(oldItemPosition);
        NoteDataItem newitem = newlist.get(newItemPosition);
        return olditem.id == newitem.id;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        NoteDataItem olditem = oldlist.get(oldItemPosition);
        NoteDataItem newitem = newlist.get(newItemPosition);
        return (olditem.title.equals(newitem.title)) && (newitem.type == olditem.type)
                && olditem.id == newitem.id;
    }
}