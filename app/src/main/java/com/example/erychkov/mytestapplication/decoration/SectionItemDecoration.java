package com.example.erychkov.mytestapplication.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author erychkov
 * @since 22.02.2018
 */

public abstract class SectionItemDecoration extends RecyclerView.ItemDecoration {


    protected final Context mContext;
    protected final Lock mLock;
    protected final int mLayoutRes;
    protected final Rule mRule;
    protected final LayoutUtils mUtils;
    private View mView;

    public SectionItemDecoration(Context context, @LayoutRes int layoutRes, Rule rule, LayoutUtils utils) {
        mContext = context;
        mLayoutRes = layoutRes;
        mRule = rule;
        mUtils = utils;
        mLock = new ReentrantLock();
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);
        final int childCount = parent.getChildCount();
        if (childCount == 0) {
            return;
        }

        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int adapterPosition = parent.getChildAdapterPosition(child);
            if (adapterPosition == RecyclerView.NO_POSITION || !mRule.isSection(adapterPosition)) {
                continue;
            }
            canvas.save();
            View section = getView(parent, adapterPosition);
            calculateSectionSize(canvas, parent, state, adapterPosition, child, section);
            section.draw(canvas);
            canvas.restore();
        }
    }

    protected abstract void calculateSectionSize(Canvas canvas, RecyclerView parent, RecyclerView.State state, int adapterPosition, View childItem, View section);

    //grabbed from here: https://yoda.entelect.co.za/view/9627/how-to-android-recyclerview-item-decorations
    protected void fixLayoutSize(View view, @NonNull ViewGroup parent) {
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

    protected View getView(ViewGroup parent, int adapterPosition) {
        mLock.lock();
        try {
            if (mView == null) {
                mView = LayoutInflater.from(mContext).inflate(mLayoutRes, parent, false);
                fixLayoutSize(mView, parent);
            }
            mRule.bindData(mView, adapterPosition);
            fixLayoutSize(mView, parent);
            return mView;
        } finally {
            mLock.unlock();
        }
    }
}
