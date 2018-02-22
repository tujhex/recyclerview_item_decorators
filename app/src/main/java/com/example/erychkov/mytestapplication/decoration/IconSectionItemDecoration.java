package com.example.erychkov.mytestapplication.decoration;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

/**
 * @author erychkov
 * @since 22.02.2018
 */


public class IconSectionItemDecoration extends RecyclerView.ItemDecoration {

    public interface Rule {
        boolean isSection(int position);

        void bindData(View view, int position);
    }

    private final Context mContext;
    private final int mLayoutRes;
    private final Rule mRule;

    public IconSectionItemDecoration(Context context, @LayoutRes int layoutRes, Rule rule) {
        mContext = context;
        mLayoutRes = layoutRes;
        mRule = rule;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        drawHorizontal(c, parent);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int childs = parent.getChildCount();
        for (int i = 0; i < childs; i++) {
            if (!mRule.isSection(i)) {
                continue;
            }
            View child = parent.getChildAt(i);
            View view = LayoutInflater.from(mContext).inflate(mLayoutRes, parent, false);
//            mRule.bindData(view, );


            final int left = view.getPaddingLeft();
            final int right = child.getPaddingLeft() + view.getPaddingRight();
            final int top = child.getTop();
            final int bottom = child.getBottom();
            view.setPadding(left, top, right, bottom);
            view.draw(canvas);
        }

    }
}
