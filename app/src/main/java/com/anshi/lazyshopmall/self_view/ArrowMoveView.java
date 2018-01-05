package com.anshi.lazyshopmall.self_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import com.anshi.lazyshopmall.R;


/**
 * @author AndyMao
 *         锁屏提示上滑箭头 
 */

public class ArrowMoveView extends View {
    private int ballWidth;

    private int ballHeight;

    public ArrowMoveView(Context context) {
        super(context);
        init();

    }

    public ArrowMoveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private float density;


    private Drawable drawable;

    @SuppressWarnings("deprecation")
    private void init() {
        density = getResources().getDisplayMetrics().density;
        ballWidth = (int) (20 * density);
        ballHeight = (int) (30 * density);
        beginTime = SystemClock.elapsedRealtime();

        drawable = getResources().getDrawable(R.drawable.pg_up_arrow);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) (ballWidth), (int) (ballHeight));
    }


    private long beginTime;

    private int duation = 3000;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();
        long time = SystemClock.elapsedRealtime();
        int delta = (int) ((time - beginTime) % duation);


        float percent = delta * 1f / duation;

        for (int i = 0; i < 3; i++) {
            int height = (int) (i * density * 9 - percent * 3 * h + h);

            Rect rect = new Rect(0, height, getWidth(), height + getWidth());
            drawable.setBounds(rect);
            int mid = (h - w / 2) / 2;
            int alpha = 255 - (int) Math.abs((1.0f * (height - mid) * 2 / (h + w / 2)) * 255);

            if (alpha > 255) {
                alpha = 255;
            }
            if (alpha < 0) {
                alpha = 0;
            }
            drawable.setAlpha(alpha);
            drawable.draw(canvas);

        }

        postInvalidateDelayed(50);
    }

}  