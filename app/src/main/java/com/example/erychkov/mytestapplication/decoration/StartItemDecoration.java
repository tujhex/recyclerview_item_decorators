package com.example.erychkov.mytestapplication.decoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author erychkov
 * @since 27.02.2018
 */

public class StartItemDecoration extends MuItemDecoration {
    public StartItemDecoration(Context context, @LayoutRes int layoutRes, Rule rule, LayoutUtils utils, ViewGroup parent) {
        super(context, layoutRes, rule, utils, parent);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if (position == RecyclerView.NO_POSITION) {
            return;
        }
        View section = getView(parent, position);
        outRect.left = section.getMeasuredWidth() + mUtils.getLayoutIndentEnd(section) + mUtils.getLayoutIndentStart(section);
    }

    @Override
    public boolean isOverlap() {
        return true;
    }

    @Override
    protected int getCanvasOffsetX(View section, int position, int start) {
        return start - (section.getMeasuredWidth() + mUtils.getLayoutIndentEnd(section));
    }

    @Override
    protected int getCanvasOffsetY(View section, int position, int start) {
        return start + mUtils.getLayoutIndentTop(section);
    }

}
