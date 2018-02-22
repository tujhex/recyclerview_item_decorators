package com.example.erychkov.mytestapplication.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author erychkov
 * @since 22.02.2018
 */

public class TopSectionItemDecoration extends RecyclerView.ItemDecoration {

    private final IconSectionItemDecoration.Rule mRule;
    private final int mLayoutRes;
    private final Context mContext;
    private final int mLayoutHeight;

    public TopSectionItemDecoration(Context context, @LayoutRes int layoutRes, @DimenRes int layoutHeight, IconSectionItemDecoration.Rule rule) {
        mContext = context;
        mLayoutRes = layoutRes;
        mLayoutHeight = layoutHeight;
        mRule = rule;
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);
        final int childs = parent.getChildCount();
        if (childs == 0) {
            return;
        }

        for (int i = 0; i < childs; i++) {
            View child = parent.getChildAt(i);
            int adapterPosition = parent.getChildAdapterPosition(child);
            if (adapterPosition == RecyclerView.NO_POSITION){
                continue;
            }
            if (!mRule.isSection(adapterPosition)) {
                continue;
            }
            canvas.save();
            View view = LayoutInflater.from(mContext).inflate(mLayoutRes, parent, false);
            fixLayoutSize(view, parent);
            mRule.bindData(view, adapterPosition);
            final int top = child.getTop() - mContext.getResources().getDimensionPixelSize(mLayoutHeight);

            canvas.translate(0, top);
            view.draw(canvas);
            canvas.restore();
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);
        if (mRule.isSection(position)) {
            outRect.top = mContext.getResources().getDimensionPixelSize(mLayoutHeight);
        }
    }

    protected void fixLayoutSize(View view, ViewGroup parent) {
        // Check if the view has a layout parameter and if it does not create one for it
        if (view.getLayoutParams() == null) {
            view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        // Create a width and height spec using the parent as an example:
        // For width we make sure that the item matches exactly what it measures from the parent.
        //  IE if layout says to match_parent it will be exactly parent.getWidth()
        int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
        // For the height we are going to create a spec that says it doesn't really care what is calculated,
        //  even if its larger than the screen
        int heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.UNSPECIFIED);

        // Get the child specs using the parent spec and the padding the parent has
        int childWidth = ViewGroup.getChildMeasureSpec(widthSpec,
            parent.getPaddingLeft() + parent.getPaddingRight(), view.getLayoutParams().width);
        int childHeight = ViewGroup.getChildMeasureSpec(heightSpec,
            parent.getPaddingTop() + parent.getPaddingBottom(), view.getLayoutParams().height);

        // Finally we measure the sizes with the actual view which does margin and padding changes to the sizes calculated
        view.measure(childWidth, childHeight);

        // And now we setup the layout for the view to ensure it has the correct sizes.
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    }
}
