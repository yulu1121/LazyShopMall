package com.anshi.lazyshopmall.view.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.anshi.lazyshopmall.R;
import com.anshi.lazyshopmall.common.BaseActivity;
import com.anshi.lazyshopmall.common.ContentFragmentAdapter;
import com.anshi.lazyshopmall.utils.DialogBuild;
import com.anshi.lazyshopmall.utils.ToastUtils;
import com.anshi.lazyshopmall.view.fragments.register.NormalLoginFragment;
import com.anshi.lazyshopmall.view.fragments.register.SmsLoginFragment;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mob.tools.utils.UIHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

import static android.R.attr.action;

/**
 *
 * Created by yulu on 2018/1/5.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener,PlatformActionListener, Handler.Callback{
    private XTabLayout mTabLayout;
    private ViewPager mViewPager;
    private AlertDialog mDialog;
    private ImageView ivWxLogin;
    private ImageView ivQqLogin;
    private ImageView ivBlog;
    private KProgressHUD progressDialog;
    private static final int MSG_ACTION_CCALLBACK = 0;
    private TextView mEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity_main);
        initView();
        initFragments();
        mDialog = new AlertDialog.Builder(this,R.style.MyDialog).create();
        addEventListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideProgressDialog();
    }

    private void initFragments() {
        List<Fragment> mFragmentsList = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        titles.add(getString(R.string.num_pass_login));
        titles.add(getString(R.string.sms_login));
        mFragmentsList.add(NormalLoginFragment.getInstance());
        SmsLoginFragment smsLoginFragment = SmsLoginFragment.getInstance();
        smsLoginFragment.setArguments(getIntent().getExtras());
        mFragmentsList.add(smsLoginFragment);
        ContentFragmentAdapter adapter = new ContentFragmentAdapter(getSupportFragmentManager(), mFragmentsList,titles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    //显示dialog
    public void showProgressDialog(String message) {
        progressDialog = DialogBuild.getBuild().createCommonLoadDialog(this,message);
        progressDialog.show();

    }

    //隐藏dialog
    public void hideProgressDialog() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    //授权
    private void authorize(Platform plat, int type) {
        switch (type) {
            case 1:
                showProgressDialog("正在打开微信");
                break;
            case 2:
                showProgressDialog("正在打开QQ");
                break;
            case 3:
                showProgressDialog("正在打开新浪微博");
                break;
        }
        if (plat.isAuthValid()) { //如果授权就删除授权资料
            plat.removeAccount(true);
        }
        plat.showUser(null);//授权并获取用户信息
    }

    private void initView() {
        ivWxLogin = (ImageView) findViewById(R.id.iv_wx_login);
        ivQqLogin = (ImageView) findViewById(R.id.iv_qq_login);
        ivBlog = (ImageView) findViewById(R.id.iv_blog);
        View mTopToolBar = findViewById(R.id.register_toolbar);
        View backInclude = mTopToolBar.findViewById(R.id.back_include);
        TextView mTextView = mTopToolBar.findViewById(R.id.toolbar_text);
        mTextView.setText("闲城登录");
        mTopToolBar.findViewById(R.id.city_text).setVisibility(View.GONE);
        mTopToolBar.findViewById(R.id.common_jump_next).setVisibility(View.GONE);
        ImageView mBackImage = backInclude.findViewById(R.id.back_image);
        mBackImage.setImageResource(R.drawable.pg_out);
        mTabLayout = (XTabLayout) findViewById(R.id.register_tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.register_vp);
        mEnter = (TextView) findViewById(R.id.register_shop_enter);
        String enter = mEnter.getText().toString();
        SpannableString spannableString = new SpannableString(enter);
        spannableString.setSpan(new UnderlineSpan(),0,enter.length(), 0);
        mEnter.setText(spannableString);
    }

    private void addEventListener() {
        ivWxLogin.setOnClickListener(this);
        ivQqLogin.setOnClickListener(this);
        ivBlog.setOnClickListener(this);
        mEnter.setOnClickListener(this);
    }

    private void initDialog(){
        View notifyView = LayoutInflater.from(this).inflate(R.layout.common_notify_layout, null);
        mDialog.setContentView(notifyView);
        Button mNotOk = notifyView.findViewById(R.id.not_ok);
        Button mOk = notifyView.findViewById(R.id.ok);
        mNotOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
    }


    public void back(View view){
        mDialog.show();
        initDialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_wx_login:
                final Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                wechat.setPlatformActionListener(this);
                wechat.SSOSetting(false);
                authorize(wechat, 1);
                break;
            case R.id.iv_qq_login:
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                qq.setPlatformActionListener(this);
                qq.SSOSetting(false);
                authorize(qq, 2);
                break;
            case R.id.iv_blog:
                Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
                sina.setPlatformActionListener(this);
                sina.SSOSetting(false);
                authorize(sina, 3);
                break;
            case R.id.register_shop_enter:
                mDialog.show();
                initDialog();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        hideProgressDialog();
        switch (msg.arg1) {
            case 1:
                // 返回成功后,首先登录App服务器,获取UserId,
                //然后再登录环信聊天服务器
                ToastUtils.showShortToast(this,"登录成功");
                break;
            case 2: // 失败
                ToastUtils.showShortToast(this,"登录失败");
                break;
            case 3:  // 取消
                ToastUtils.showShortToast(this,"取消登录");
                break;
        }
        return false;
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 1;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);   //发送消息
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 2;
        msg.arg2 = action;
        msg.obj = throwable;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 3;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideProgressDialog();
    }

    @Override
    public void onBackPressed() {
        hideProgressDialog();
        finishAffinity();

        super.onBackPressed();
    }
}
