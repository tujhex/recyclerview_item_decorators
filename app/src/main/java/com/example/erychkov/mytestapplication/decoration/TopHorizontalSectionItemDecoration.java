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

public class TopHorizontalSectionItemDecoration extends SectionItemDecoration {

    public TopHorizontalSectionItemDecoration(Context context, @LayoutRes int layoutRes, Rule rule, LayoutUtils layoutUtils) {
        super(context, layoutRes, rule, layoutUtils);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if (mRule.isSection(position)) {
            View section = getView(parent);
            outRect.top = section.getMeasuredHeight() + mUtils.getLayoutIndentTop(section) + mUtils.getLayoutIndentBottom(section);
        }
    }

    @Override
    protected void calculateSectionSize(Canvas canvas, RecyclerView parent, RecyclerView.State state, int adapterPosition, View childItem, View section) {
        final int offsetDy = section.getMeasuredHeight() + mUtils.getLayoutIndentBottom(section);
        canvas.translate(mUtils.getLayoutIndentTop(section), childItem.getTop() - offsetDy);
    }
}
