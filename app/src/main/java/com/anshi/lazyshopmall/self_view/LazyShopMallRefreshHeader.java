package com.anshi.lazyshopmall.self_view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.OnAnimEndListener;

/**
 *
 * Created by yulu on 2018/1/15.
 */

public class LazyShopMallRefreshHeader extends FrameLayout implements IHeaderView {
    private Context mContext;
    public LazyShopMallRefreshHeader(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public LazyShopMallRefreshHeader(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public View getView() {

        return null;
    }

    @Override
    public void onPullingDown(float fraction, float maxHeadHeight, float headHeight) {

    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {

    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {

    }

    @Override
    public void onFinish(OnAnimEndListener animEndListener) {

    }

    @Override
    public void reset() {

    }
}
