package com.example.erychkov.mytestapplication.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author erychkov
 * @since 22.02.2018
 */

public class TopHorizontalSectionItemDecoration extends SectionItemDecoration {

    private final int mHeaderHeight;

    public TopHorizontalSectionItemDecoration(Context context, @LayoutRes int layoutRes, Rule rule, @DimenRes int headerHeight) {
        super(context, layoutRes, rule);
        mHeaderHeight = mContext.getResources().getDimensionPixelSize(headerHeight);
    }

    @Override
    protected void calculateSectionSize(Canvas canvas, RecyclerView parent, RecyclerView.State state, int adapterPosition, View item) {
        final int top = item.getTop() - mHeaderHeight;
        canvas.translate(0, top);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if (mRule.isSection(position)) {
            outRect.top = mHeaderHeight;
        }
    }
}
