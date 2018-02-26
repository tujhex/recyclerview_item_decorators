package com.example.erychkov.mytestapplication.decoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author erychkov
 * @since 22.02.2018
 */

public class StartVerticalSectionItemDecoration extends SectionItemDecoration {

    public StartVerticalSectionItemDecoration(Context context, @LayoutRes int layoutRes, Rule rule, LayoutUtils layoutUtils, ItemDecorationManager manager) {
        super(context, layoutRes, rule, layoutUtils, manager);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if (position == RecyclerView.NO_POSITION) {
            return;
        }
        View section = getView(parent);
        bindData(section, parent, position);
        outRect.left = section.getMeasuredWidth() + mUtils.getLayoutIndentEnd(section) + mUtils.getLayoutIndentStart(section);
    }

    @Override
    protected int getCanvasOffsetX(View section, View child, Rect sectionBounds, Rect decoratedBounds) {
        return child.getLeft() - (section.getMeasuredWidth() + mUtils.getLayoutIndentEnd(section));
    }

    @Override
    protected int getCanvasOffsetY(View section, View child, Rect sectionBounds, Rect decoratedBounds) {
        return child.getTop() + mUtils.getLayoutIndentTop(section);
    }
}
