package com.cloudsys.smashintl.itemdecorator;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by azmin on 30/7/16.
 */
public class ItemDecorationGrid extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int spacing;

    public ItemDecorationGrid(int spanCount, int spacing) {
        this.spanCount = spanCount;
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column

        Log.e("dec", "EX");
        outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
        outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
        if (position >= spanCount) {
            outRect.top = spacing; // item top
        }
    }

}