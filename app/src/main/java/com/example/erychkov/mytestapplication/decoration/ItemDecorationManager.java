package com.example.erychkov.mytestapplication.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author erychkov
 * @since 26.02.2018
 */

public class ItemDecorationManager {
    private final List<RecyclerView.ItemDecoration> mItemDecorations;

    public ItemDecorationManager() {
        mItemDecorations = new ArrayList<>();
    }

    public void addItemDecoration(RecyclerView recyclerView, RecyclerView.ItemDecoration itemDecoration) {
        recyclerView.addItemDecoration(itemDecoration);
        mItemDecorations.add(itemDecoration);

    }

    public void addItemDecoration(RecyclerView recyclerView, RecyclerView.ItemDecoration itemDecoration, int index) {
        recyclerView.addItemDecoration(itemDecoration, index);
        mItemDecorations.add(index, itemDecoration);
    }

    public int getIndexOf(RecyclerView.ItemDecoration itemDecoration) {
        return mItemDecorations.indexOf(itemDecoration);
    }

    public RecyclerView.ItemDecoration get(int index) {
        return mItemDecorations.get(index);
    }

    public int getCount(){
        return mItemDecorations.size();
    }
}
