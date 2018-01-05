package com.anshi.lazyshopmall.self_view;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/***
 *
 * 从下向上滑结束Activity
 *
 * @author 帽檐遮不住阳光
 *
 */
public class BottomToTopFinishLayout extends RelativeLayout {

    /**
     * BottomFinishLayout布局的父布局
     */
    private ViewGroup mParentView;
    /**
     * 滑动的最小距离
     */
    private int mTouchSlop;
    /**
     * 按下点的X坐标
     */
    private int downX;
    /**
     * 按下点的Y坐标
     */
    private int downY;
    /**
     * 临时存储X坐标
     */
    private int tempY;
    /**
     * 滑动类
     */
    private Scroller mScroller;
    /**
     * BottomFinishLayout的宽度
     */
    private int viewHeight;

    private boolean isSilding;

    private OnFinishListener onFinishListener;
    private boolean isFinish;

    public BottomToTopFinishLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomToTopFinishLayout(Context context, AttributeSet attrs,
                                   int defStyle) {
        super(context, attrs, defStyle);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mScroller = new Scroller(context);
    }

    /**
     * 事件拦截操作
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getRawX();
                downY = tempY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) ev.getRawY();
                // 满足此条件屏蔽SildingFinishLayout里面子类的touch事件
                if (Math.abs(downY - moveY) > mTouchSlop
                        && Math.abs((int) ev.getRawX() - downX) < mTouchSlop) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) event.getRawY();// 触摸点相对于屏幕的位置
                int deltaY = moveY - tempY;
                tempY = moveY;
                if (Math.abs(downY - moveY) > mTouchSlop
                        && Math.abs((int) event.getRawX() - downX) < mTouchSlop) {
                    isSilding = true;
                }

                if (downY - moveY >= 0 && isSilding) {
                    mParentView.scrollBy(0, -deltaY);
                }
                break;
            case MotionEvent.ACTION_UP:
                isSilding = false;
                System.out.println("vvv===========" + mParentView.getScrollY());
                System.out.println("/3============" + viewHeight / 3);
                if (mParentView.getScrollY() >= viewHeight / 3) {
                    isFinish = true;
                    scrollTop();
                } else {
                    scrollOrigin();
                    isFinish = false;
                }
                break;
        }
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            mParentView = (ViewGroup) this.getParent();
            viewHeight = this.getHeight();
        }
    }

    /***
     * 接口回调
     */
    public void setOnFinishListener(OnFinishListener onSildingFinishListener) {
        this.onFinishListener = onSildingFinishListener;
    }

    /**
     * 滚动出界面
     */
    private void scrollTop() {
        final int delta = (viewHeight - mParentView.getScrollY());
        mScroller.startScroll(0, mParentView.getScrollY(), 0, delta - 1,
                Math.abs(delta));
        postInvalidate();
    }

    /**
     * 滚动到起始位置
     */
    private void scrollOrigin() {
        int delta = mParentView.getScrollY();
        mScroller.startScroll(0, mParentView.getScrollY(), 0, -delta,
                Math.abs(delta));
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            mParentView.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
            if (mScroller.isFinished() && isFinish) {
                if (onFinishListener != null) {
                    onFinishListener.onFinish();
                } else {
                    // 没有设置OnSildingFinishListener，让其滚动到其实位置
                    scrollOrigin();
                    isFinish = false;
                }
            }
        }
    }

    public interface OnFinishListener {
        public void onFinish();
    }

}
