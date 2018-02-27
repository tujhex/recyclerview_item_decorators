package com.example.erychkov.mytestapplication.decoration;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author erychkov
 * @since 26.02.2018
 */

public class ItemDecorationManager extends RecyclerView.ItemDecoration {
    private final List<MuItemDecoration> mItemDecorations;

    public ItemDecorationManager() {
        mItemDecorations = new ArrayList<>();
    }

    public void addItemDecoration(MuItemDecoration itemDecoration) {
        mItemDecorations.add(0, itemDecoration);
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);
        final int childCount = parent.getChildCount();
        if (childCount == 0) {
            return;
        }

        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
            if (position == RecyclerView.NO_POSITION) {
                continue;
            }
            Point translate = new Point(child.getLeft(), child.getTop());
            Point tempTranslate = new Point(translate);
            for (MuItemDecoration itemDecoration : mItemDecorations) {
                if (!itemDecoration.shouldDraw(position)) {
                    continue;
                }
                View view = itemDecoration.getView(parent, position);
                Point section;
                if (!itemDecoration.isOverlap()) {
                    section = itemDecoration.getCanvasTranslation(parent, position, translate);
                } else {
                    section = itemDecoration.getCanvasTranslation(parent, position, tempTranslate);
                }
                canvas.save();
                canvas.translate(section.x, section.y);
                view.draw(canvas);
                canvas.restore();

                if (!itemDecoration.isOverlap()) {
                    translate.x = section.x;
                    translate.y = section.y;
                }
            }
        }

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        Rect temp = new Rect(0, 0, 0, 0);
        for (RecyclerView.ItemDecoration itemDecoration : mItemDecorations) {
            temp.set(0,0,0,0);
            itemDecoration.getItemOffsets(temp, view, parent, state);
            outRect.left += temp.left;
            outRect.right += temp.right;
            outRect.top += temp.top;
            outRect.bottom += temp.bottom;
        }
    }

    public RecyclerView.ItemDecoration get(int index) {
        return mItemDecorations.get(index);
    }

    public int getCount() {
        return mItemDecorations.size();
    }
}
