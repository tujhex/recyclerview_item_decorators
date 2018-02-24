package com.example.erychkov.mytestapplication.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author erychkov
 * @since 22.02.2018
 */

public class StartVerticalSectionItemDecoration extends SectionItemDecoration {

    public StartVerticalSectionItemDecoration(Context context, @LayoutRes int layoutRes, Rule rule, LayoutUtils layoutUtils) {
        super(context, layoutRes, rule, layoutUtils);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        View section = getView(parent);
        outRect.left = section.getMeasuredWidth() + mUtils.getLayoutIndentEnd(section) + mUtils.getLayoutIndentStart(section);
    }

    @Override
    protected void calculateSectionSize(Canvas canvas, RecyclerView parent, RecyclerView.State state, int adapterPosition, View childItem, View section) {
        final int offsetDx = section.getMeasuredWidth() + mUtils.getLayoutIndentEnd(section);
        final int offsetDy = -mUtils.getLayoutIndentTop(section);
        canvas.translate(childItem.getLeft() - offsetDx, childItem.getTop() - offsetDy);
    }
}
