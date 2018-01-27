package com.anshi.lazyshopmall.view.fragments.register;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.anshi.lazyshopmall.R;

/**
 *
 * Created by yulu on 2018/1/5.
 */

public class NormalLoginFragment extends Fragment {
    private Context mContext;
    private EditText mPassword;
    private EditText mNumber;
    private RadioButton mCanSeePass;
    private boolean canSee;
    public static NormalLoginFragment getInstance(){
         return new NormalLoginFragment();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addEventListener();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.normal_login_frag_main,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mNumber = view.findViewById(R.id.num_input);
        mPassword = view.findViewById(R.id.pass_input);
        mCanSeePass = view.findViewById(R.id.can_see_pass);
    }

    private void addEventListener(){

        mCanSeePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通过全局的一个变量的设置，这个就是判断控件里面的内容是不是能被看到
                if (!canSee){
                    //如果是不能看到密码的情况下，
                    mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    canSee=true;
                    mCanSeePass.setChecked(true);
                }else {
                    //如果是能看到密码的状态下
                    mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    canSee=false;
                    mCanSeePass.setChecked(false);
                }
            }
        });
    }
}
