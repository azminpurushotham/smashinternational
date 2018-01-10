package com.cloudsys.smashintl.itemdecorator;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by azmin on 1/8/16.
 */
public class DividerItemDecorationList extends RecyclerView.ItemDecoration {

    private int space;

    public DividerItemDecorationList(int integer) {
        this.space = integer;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildPosition(view) == 0){
            outRect.top = space;
        }
    }

}
