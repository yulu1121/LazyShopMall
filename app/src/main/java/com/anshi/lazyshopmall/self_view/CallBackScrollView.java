package com.anshi.lazyshopmall.self_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 *
 * Created by yulu on 2017/8/30.
 */

public class CallBackScrollView extends ScrollView {
    // 拖动的距离 size = 4 的意思 只允许拖动屏幕的1/4  
    public static final int size = 4;
    private View inner;
    private float y;
    private Rect normal=new Rect();
    public CallBackScrollView(Context context) {
        super(context);
    }

    public CallBackScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onFinishInflate() {
        if (getChildCount()>0){
            inner = getChildAt(0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(null==inner){
            return super.onTouchEvent(ev);
        }else {
            commOnTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }
    public void commOnTouchEvent(MotionEvent motionEvent){
        int action = motionEvent.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                y = motionEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (isNeedAnimation()){
                    animation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                final float preY = y;
                float nowY = motionEvent.getY();
                int deltaY = (int) (preY - nowY)/size;
               //scrollBy(0,deltaY);
                y = nowY;
                if(isNeedMove()){
                    if (normal.isEmpty()){
                        normal.set(inner.getLeft(),inner.getTop(),inner.getRight(),inner.getBottom());
                        return;
                    }
                    int yy = inner.getTop() - deltaY;
                    inner.layout(inner.getLeft(),yy,inner.getRight(),inner.getBottom()-deltaY);
                }
                break;
            default:
                break;
        }
    }
    public void animation(){
        TranslateAnimation ta = new TranslateAnimation(0,0,inner.getTop(),normal.top);
        ta.setDuration(500);
        inner.layout(normal.left,normal.top,normal.right,normal.bottom);
        normal.setEmpty();
    }
    public boolean isNeedAnimation(){
        return !normal.isEmpty();

    }
    public boolean isNeedMove(){
        int offset = inner.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        return scrollY == 0 || scrollY == offset;
    }

}
