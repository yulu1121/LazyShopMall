package com.anshi.lazyshopmall.view.activity.goodsdetail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.anshi.lazyshopmall.R;
import com.anshi.lazyshopmall.common.BaseActivity;
import com.anshi.lazyshopmall.utils.DialogBuild;
import com.kaopiz.kprogresshud.KProgressHUD;

/**
 *
 * Created by yulu on 2018/1/20.
 */

public class GoodsDetailActivity extends BaseActivity {
    private WebView mWebView;
    private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_detail_activity_main);
        initView();
        addEventListener();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        view = findViewById(R.id.goods_toolbar);
        TextView textView = view.findViewById(R.id.toolbar_text);
        textView.setText("商品详情");
        view.findViewById(R.id.city_text).setVisibility(View.GONE);
        view.findViewById(R.id.common_jump_next).setVisibility(View.GONE);

        mWebView = (WebView) findViewById(R.id.goods_detail_webview);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        // 关闭缩放
        settings.setBuiltInZoomControls(false);
        settings.setSupportZoom(false);
        // 设定缩放控件隐藏
        settings.setDisplayZoomControls(false);
        // 设置加载进来的页面自适应手机屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
    }

    private void addEventListener(){
        mWebView.loadUrl("file:///android_asset/dist/index.html");
        final KProgressHUD commonProgressDialog = DialogBuild.getBuild().createCommonProgressDialog(this, "加载中...");
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress==100){
                    commonProgressDialog.dismiss();
                }else {
                    commonProgressDialog.setProgress(newProgress);
                };
            }
        });
    }

    public void back(View view){
        finish();
    }
}
