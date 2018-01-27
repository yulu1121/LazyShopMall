package com.anshi.lazyshopmall.view.fragments.register;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.anshi.lazyshopmall.R;
import com.anshi.lazyshopmall.utils.Constants;
import com.anshi.lazyshopmall.utils.ToastUtils;
import com.anshi.lazyshopmall.utils.Utils;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;



/**
 *
 * Created by yulu on 2018/1/5.
 */

public class SmsLoginFragment extends Fragment {
    public static final String TAG = "xxx";
    private Context mContext;
    private boolean tag = true;
    private int i = 120;
    private EditText mPhoneInput;
    private RadioButton mVerificationBtn;
    private EditText mTextView;
    private Button mLoginBtn;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case 0:
                    //客户端验证成功，可以进行注册,返回校验的手机和国家代码phone/country
                    ToastUtils.showShortToast(mContext,"验证成功");
                    break;
                case 1:
                    //获取验证码成功
                    ToastUtils.showShortToast(mContext,"获取验证码成功");
                    break;
                case 2:
                    //返回支持发送验证码的国家列表
                    Log.e("xxx",msg.obj.toString());
                    break;
                case 3:
                    ToastUtils.showShortToast(mContext,"验证失败");
                    break;
            }
        }
    };
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventHandler eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        Message msg = new Message();
                        msg.arg1 = 0;
                        msg.obj = data;
                        handler.sendMessage(msg);
                        Log.d(TAG, getString(R.string.verify_submit_success));
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //提交验证码成功
                        Message msg = new Message();
                        //获取验证码成功
                        msg.arg1 = 1;
                        msg.obj = R.string.forget_has_verify_success;
                        handler.sendMessage(msg);
                        Log.d(TAG, getString(R.string.forget_has_verify_success));

                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        Message msg = new Message();
                        //返回支持发送验证码的国家列表
                        msg.arg1 = 2;
                        msg.obj = R.string.verify_callback_country;
                        handler.sendMessage(msg);
                        Log.d(TAG, getString(R.string.verify_callback_country));
                    }
                } else {
                    Message msg = new Message();
                    //返回支持发送验证码的国家列表
                    msg.arg1 = 3;
                    msg.obj = R.string.forget_verify_fail;
                    handler.sendMessage(msg);
                    Log.d(TAG, getString(R.string.forget_verify_fail));
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
    }

    public static SmsLoginFragment getInstance(){
        return new SmsLoginFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addEventListener();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sms_login_layout,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mPhoneInput = view.findViewById(R.id.input_phone_sms);
        mVerificationBtn = view.findViewById(R.id.sms_has_verification);
        mTextView = view.findViewById(R.id.how_long_seconds);
        mLoginBtn = view.findViewById(R.id.sms_login);
        ImageView mPersonHead = view.findViewById(R.id.person_head);
        String sex = getArguments().getString("sex");
        if (!TextUtils.isEmpty(sex)){
            if (sex.equals(Constants.MAN)){
                mPersonHead.setImageResource(R.drawable.head);
            }else {
                mPersonHead.setImageResource(R.drawable.woman_head);
            }
        }
    }

    private void addEventListener(){
        mVerificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isMobileNO(mPhoneInput.getText().toString())){
                    mVerificationBtn.setClickable(true);
                    SMSSDK.getSupportedCountries();
                    SMSSDK.getVerificationCode("86", mPhoneInput.getText().toString());
                    changeBtnGetCode(mVerificationBtn);
                }else {
                    ToastUtils.showShortToast(mContext,"手机号码格式有误");
                }
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mPhoneInput.getText().toString())){
                    ToastUtils.showShortToast(mContext,"请输入手机号码");
                    return;
                }
                if (TextUtils.isEmpty(mTextView.getText().toString())){
                    ToastUtils.showShortToast(mContext,"请输入验证码");
                    return;
                }
                SMSSDK.submitVerificationCode("86", mPhoneInput.getText().toString(),mTextView.getText().toString());
            }
        });
    }

    /*
  * 改变按钮样式
  * */
    private void changeBtnGetCode( final RadioButton button) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                if (tag) {
                    while (i > 0) {
                        i--;
                        //如果活动为空
                        ((Activity)mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                               button.setText("获取验证码(" + i + ")");
                               button.setClickable(false);
                            }
                        });
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    tag = false;
                }
                i = 120;
                tag = true;
                ((Activity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        button.setClickable(true);
                    }
                });
            }
        };
        thread.start();
    }
}
