package com.example.erychkov.mytestapplication.decoration;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * @author erychkov
 * @since 27.02.2018
 */

public abstract class MuItemDecoration extends RecyclerView.ItemDecoration {
    protected final Context mContext;
    protected final int mLayoutRes;
    protected final Rule mRule;
    protected final LayoutUtils mUtils;
    private final Map<Integer, View> mViews;


    public MuItemDecoration(Context context, @LayoutRes int layoutRes, Rule rule, LayoutUtils utils, ViewGroup parent) {
        mContext = context;
        mLayoutRes = layoutRes;
        mRule = rule;
        mUtils = utils;
        mViews = new HashMap<>();
    }

    public final Point getCanvasTranslation(ViewGroup parent, int position, Point itemBorders) {
        View section = getView(parent, position);
        return new Point(getCanvasOffsetX(section, position, itemBorders.x), getCanvasOffsetY(section, position, itemBorders.y));
    }

    public abstract boolean isOverlap();

    public final boolean shouldDraw(int position) {
        return mRule.isSection(position);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if (mRule.isSection(position) && position != RecyclerView.NO_POSITION) {
            getView(parent, position);
        }
    }

    protected abstract int getCanvasOffsetX(View section, int position, int start);

    protected abstract int getCanvasOffsetY(View section, int position, int start);


    protected final View getView(ViewGroup parent, int position) {
        View view = mViews.get(position);
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(mLayoutRes, parent, false);
            fixLayoutSize(view, parent);
//            view.layout(0, 0, 1, 1);
            mRule.bindData(view, position);
            view.requestLayout();
//            fixLayoutSize(view, parent);
            mViews.put(position, view);
        }
        fixLayoutSize(view, parent);
        return view;
    }


    //grabbed from here: https://yoda.entelect.co.za/view/9627/how-to-android-recyclerview-item-decorations
    protected final void fixLayoutSize(@NonNull View view, @NonNull ViewGroup parent) {
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
}
