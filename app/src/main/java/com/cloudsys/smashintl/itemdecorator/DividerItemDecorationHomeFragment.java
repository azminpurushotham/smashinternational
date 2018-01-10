package com.cloudsys.smashintl.itemdecorator;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by azmin on 1/8/16.
 */
public class DividerItemDecorationHomeFragment extends RecyclerView.ItemDecoration {

    private int space;

    public DividerItemDecorationHomeFragment(int integer) {
        this.space = integer;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildPosition(view) == 0) {
            outRect.top = space;
            outRect.left = space;
            outRect.right = space;
        } else if (parent.getChildLayoutPosition(view) == 1) {
            outRect.top = space;
            outRect.left = space;
            outRect.right = space;
        } else {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
        }


    }

}
