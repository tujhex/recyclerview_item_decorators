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

public class TopHorizontalSectionItemDecoration extends SectionItemDecoration {

    public TopHorizontalSectionItemDecoration(Context context, @LayoutRes int layoutRes, Rule rule, LayoutUtils layoutUtils, ItemDecorationManager manager) {
        super(context, layoutRes, rule, layoutUtils, manager);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if (position == RecyclerView.NO_POSITION || !mRule.isSection(position)) {
            return;
        }
        View section = getView(parent);
        bindData(section, parent, position);
        outRect.top = section.getMeasuredHeight() + mUtils.getLayoutIndentTop(section) + mUtils.getLayoutIndentBottom(section);
    }

    @Override
    protected int getCanvasOffsetX(View section, View child, Rect sectionBounds, Rect decoratedBounds) {
        return mUtils.getLayoutIndentStart(section);
    }

    @Override
    protected int getCanvasOffsetY(View section, View child, Rect sectionBounds, Rect decoratedBounds) {
        int sectionHeight = section.getMeasuredHeight() + mUtils.getLayoutIndentBottom(section);
        return child.getTop() - (sectionHeight + sectionBounds.top);
    }
}
