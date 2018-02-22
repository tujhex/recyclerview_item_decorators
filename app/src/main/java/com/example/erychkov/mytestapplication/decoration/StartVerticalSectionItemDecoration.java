package com.example.erychkov.mytestapplication.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DimenRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author erychkov
 * @since 22.02.2018
 */

public class StartVerticalSectionItemDecoration extends SectionItemDecoration {

    private final int mHeaderWidth;
    private final int mSidePadding;

    public StartVerticalSectionItemDecoration(Context context, @LayoutRes int layoutRes, Rule rule, @DimenRes int headerWidth, @DimenRes int sidePadding) {
        super(context, layoutRes, rule);
        mHeaderWidth = mContext.getResources().getDimensionPixelSize(headerWidth);
        mSidePadding = mContext.getResources().getDimensionPixelSize(sidePadding);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = mHeaderWidth + mSidePadding*2;
    }

    @Override
    protected void calculateSectionSize(Canvas canvas, RecyclerView parent, RecyclerView.State state, int adapterPosition, View item) {
        final int left = item.getLeft() - mHeaderWidth - mSidePadding;
        canvas.translate(left, item.getTop());
    }
}
