package com.example.erychkov.mytestapplication.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author erychkov
 * @since 22.02.2018
 */

public abstract class SectionItemDecoration extends RecyclerView.ItemDecoration {


    protected final Context mContext;
    protected final int mLayoutRes;
    protected final Rule mRule;
    protected final LayoutUtils mUtils;
    protected final ItemDecorationManager mManager;

    public SectionItemDecoration(Context context, @LayoutRes int layoutRes, Rule rule, LayoutUtils utils, ItemDecorationManager manager) {
        mContext = context;
        mLayoutRes = layoutRes;
        mRule = rule;
        mUtils = utils;
        mManager = manager;
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
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
            View section = getView(parent);
            bindData(section, parent, adapterPosition);
            Rect outBounds = getSectionRect(child, parent, state);
            Rect decoratedBounds = new Rect();
            parent.getDecoratedBoundsWithMargins(child, decoratedBounds);
            canvas.save();
            canvas.translate(getCanvasOffsetX(section, child, outBounds, decoratedBounds), getCanvasOffsetY(section, child, outBounds, decoratedBounds));
            section.draw(canvas);
            canvas.restore();
        }
    }

    protected void bindData(View view, ViewGroup parent, int position) {
        fixLayoutSize(view, parent);
        mRule.bindData(view, position);
    }

    protected abstract int getCanvasOffsetX(View section, View child, Rect sectionBounds, Rect decoratedBounds);

    protected abstract int getCanvasOffsetY(View section, View child, Rect sectionBounds, Rect decoratedBounds);

    //grabbed from here: https://yoda.entelect.co.za/view/9627/how-to-android-recyclerview-item-decorations
    protected void fixLayoutSize(View view, @NonNull ViewGroup parent) {
        // Check if the view has a layout parameter and if it does not create one for it
        if (view.getLayoutParams() == null) {
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        // Create a width and height spec using the parent as an example:
        // For width we make sure that the item matches exactly what it measures from the parent.
        //  IE if layout says to match_parent it will be exactly parent.getWidth()
        int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
        // For the height we are going to create a spec that says it doesn't really care what is calculated,
        //  even if its larger than the screen
        int heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.UNSPECIFIED);

        // Get the child specs using the parent spec and the padding the parent has
        int childWidth = ViewGroup.getChildMeasureSpec(widthSpec, parent.getPaddingLeft() + parent.getPaddingRight(), view.getLayoutParams().width);
        int childHeight = ViewGroup.getChildMeasureSpec(heightSpec, parent.getPaddingTop() + parent.getPaddingBottom(), view.getLayoutParams().height);

        // Finally we measure the sizes with the actual view which does margin and padding changes to the sizes calculated
        view.measure(childWidth, childHeight);

        // And now we setup the layout for the view to ensure it has the correct sizes.
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    }


    protected Rect getSectionRect(View child, RecyclerView parent, RecyclerView.State state) {
        Rect bounds = new Rect();
        Rect temp = new Rect();
        for (int i = mManager.getCount() - 1; i > 0; i--) {
            temp.set(0, 0, 0, 0);
            RecyclerView.ItemDecoration decoration = mManager.get(i);
            if (decoration == this) {
                break;

            }
            decoration.getItemOffsets(temp, child, parent, state);
            bounds.left += temp.left;
            bounds.right += temp.right;
            bounds.bottom += temp.bottom;
            bounds.top += temp.top;
        }
        return bounds;
    }

    protected View getView(ViewGroup parent) {
        return LayoutInflater.from(mContext).inflate(mLayoutRes, parent, false);
    }
}
