package com.anshi.lazyshopmall.self_view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 *
 * Created by yulu on 2018/1/12.
 */

public class CanScrollGridView extends GridView {
    public CanScrollGridView(Context context) {
        super(context);
    }

    public CanScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
