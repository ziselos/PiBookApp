package gr.mobile.zisis.pibook.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by zisis on 2912//17.
 */

public class GridSpacingDecoration extends RecyclerView.ItemDecoration {

    private int span;
    private int space;
    private boolean include;
    private Context context;

    public GridSpacingDecoration(int span, int space, boolean include) {
        this.span = span;
        this.space = space;
        this.include = include;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int column = position % span;

        if (include) {
            outRect.left = space - column * space / span;
            outRect.right = (column + 1) * space / span;

            if (position < span) {
                outRect.top = space;
            }
            outRect.bottom = space;
        } else {
            outRect.left = column * space / span;
            outRect.right = space - (column + 1) * space / span;
            if (position >= span) {
                outRect.top = space;
            }
        }

    }
}
