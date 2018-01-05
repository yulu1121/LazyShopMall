package com.anshi.lazyshopmall.common;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.anshi.lazyshopmall.utils.ToastUtils;

/**
 *
 * Created by yulu on 2018/1/3.
 */

public class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        mContext = BaseActivity.this;

    }


    @Override
    protected void onPause() {
        super.onPause();
        ToastUtils.cancelToast();
    }
}
