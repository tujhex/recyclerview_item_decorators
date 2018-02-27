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

public class TopItemDecoration extends MuItemDecoration {
    public TopItemDecoration(Context context, @LayoutRes int layoutRes, Rule rule, LayoutUtils utils, ViewGroup parent) {
        super(context, layoutRes, rule, utils, parent);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if (position == RecyclerView.NO_POSITION || !mRule.isSection(position)) {
            return;
        }
        View section = getView(parent, position);
        outRect.top = section.getMeasuredHeight() + mUtils.getLayoutIndentTop(section) + mUtils.getLayoutIndentBottom(section);
    }

    @Override
    public boolean isOverlap() {
        return false;
    }

    @Override
    protected int getCanvasOffsetX(View section, int position, int start) {
        return mUtils.getLayoutIndentStart(section);
    }

    @Override
    protected int getCanvasOffsetY(View section, int position, int start) {
        return start - (section.getMeasuredHeight() + mUtils.getLayoutIndentBottom(section));
    }
}
