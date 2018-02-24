package com.example.erychkov.mytestapplication.decoration;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author tujhex
 * @since 24.02.2018
 */

public class LayoutUtils {
    public int getLayoutIndentStart(View view) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            return layoutParams.getMarginStart();
        }
        return layoutParams.leftMargin;
    }

    public int getLayoutIndentEnd(View view) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            return layoutParams.getMarginEnd();
        }
        return layoutParams.rightMargin;
    }

    public int getLayoutIndentTop(View view) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        return layoutParams.topMargin;
    }

    public int getLayoutIndentBottom(View view) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        return layoutParams.bottomMargin;
    }
}
