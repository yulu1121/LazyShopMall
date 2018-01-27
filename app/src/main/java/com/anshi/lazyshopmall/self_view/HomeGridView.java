package com.anshi.lazyshopmall.self_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 *
 * Created by yulu on 2017/2/22.
 */

public class HomeGridView extends GridView {
    public HomeGridView(Context context) {
        this(context,null);
    }

    public HomeGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param ev
     * @return
     * 不让gridview滚动
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return ev.getAction() == MotionEvent.ACTION_MOVE || super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        requestDisallowInterceptTouchEvent(false);
        return super.onInterceptTouchEvent(ev);
    }
}
