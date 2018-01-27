package com.anshi.lazyshopmall.self_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.liuting.sliderlayout.SliderLayout;

/**
 *
 * Created by yulu on 2018/1/12.
 */

public class CanScrollSliderLayout extends SliderLayout {
    public CanScrollSliderLayout(Context context) {
        super(context);
    }

    public CanScrollSliderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CanScrollSliderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        requestDisallowInterceptTouchEvent(true);
        return super.onInterceptTouchEvent(ev);
    }
}
