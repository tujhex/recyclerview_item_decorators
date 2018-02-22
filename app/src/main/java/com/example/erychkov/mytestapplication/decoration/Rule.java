package com.example.erychkov.mytestapplication.decoration;

import android.view.View;

/**
 * @author erychkov
 * @since 22.02.2018
 */
public interface Rule {
    boolean isSection(int position);

    void bindData(View view, int position);

}
