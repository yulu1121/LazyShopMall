package com.anshi.lazyshopmall.view.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import android.support.annotation.IdRes;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.anshi.lazyshopmall.R;
import com.anshi.lazyshopmall.common.BaseActivity;
import com.anshi.lazyshopmall.common.ContentViewAdapter;
import com.anshi.lazyshopmall.utils.Constants;


import java.util.ArrayList;
import java.util.List;

import me.kaelaela.verticalviewpager.VerticalViewPager;
import me.kaelaela.verticalviewpager.transforms.DefaultTransformer;

/**
 *
 * Created by yulu on 2018/1/3.
 */

public class WelcomeActivity extends BaseActivity {
    private VerticalViewPager verticalViewPager;
    private List<View> views;
    private Button nextBtn;
    private View backView;
    private View ageBackView;
    private RadioButton man;
    private RadioButton woman;
    private TextView mTextView;
    private TextView mJumpNext;
    private TextView mAgeJumpNext;
    private RadioGroup mAgeRadioGroup;
    private RadioButton nineAge;
    private RadioButton eightAge;
    private RadioButton sevenAge;
    private RadioButton nothingAge;
    private ContentViewAdapter adapter;
    private ImageView mAgeSelectImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity_main);
        initView();
        initData();
        initListener();
    }

    private void initData() {
        views = new ArrayList<>();
        View first = LayoutInflater.from(this).inflate(R.layout.welcome_first, null);
        View second = LayoutInflater.from(this).inflate(R.layout.welcome_second, null);
        views.add(first);
        views.add(second);
        nextBtn =  first.findViewById(R.id.first_btn);
        backView = second.findViewById(R.id.common_toolbar_layout);
        backView.findViewById(R.id.back_include).setVisibility(View.GONE);
        mTextView = backView.findViewById(R.id.toolbar_text);
        mTextView.setText(R.string.lazy_shop_mall_travel);
        mJumpNext = backView.findViewById(R.id.common_jump_next);
        man = second.findViewById(R.id.man_rb);
        woman = second.findViewById(R.id.woman_rb);
        adapter = new ContentViewAdapter(views);
        verticalViewPager.setAdapter(adapter);
        verticalViewPager.setPageTransformer(false, new DefaultTransformer());
    }
    //在选择性别后，第三个界面进行初始化
    private View initAgePage(String type){
        String text = getString(R.string.please_select_age);
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ForegroundColorSpan(Color.RED),2,text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        View third = LayoutInflater.from(this).inflate(R.layout.welcome_age_choice,null);
        ageBackView = third.findViewById(R.id.common_toolbar_layout);
        mAgeRadioGroup = third.findViewById(R.id.age_radio_group);
        TextView ageTextOne = third.findViewById(R.id.age_text_one);
        mAgeSelectImage = third.findViewById(R.id.age_select_image);
        if (type.equals(Constants.MAN)){
            mAgeSelectImage.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.pg_welcome_man));
        }else {
            mAgeSelectImage.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.pg_welcome_woman));
        }
        ageTextOne.setText(spannableString);
        nineAge = third.findViewById(R.id.nine_age);
        eightAge = third.findViewById(R.id.eight_age);
        sevenAge = third.findViewById(R.id.seven_age);
        nothingAge = third.findViewById(R.id.noting_age);
        ageBackView.findViewById(R.id.back_include).setVisibility(View.GONE);
        mAgeJumpNext = ageBackView.findViewById(R.id.common_jump_next);
        TextView mTextView = ageBackView.findViewById(R.id.toolbar_text);
        mTextView.setText(R.string.lazy_shop_mall_travel);
        mAgeJumpNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WelcomeActivity.this, "跳转", Toast.LENGTH_SHORT).show();
            }
        });
        mAgeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                Intent intent = new Intent(WelcomeActivity.this, RegisterActivity.class);
                switch (checkedId){
                    case R.id.nine_age:
                        eightAge.setChecked(false);
                        sevenAge.setChecked(false);
                        nothingAge.setChecked(false);
                        setColor();
                        nineAge.setTextColor(Color.WHITE);
                        intent.putExtra("age",nineAge.getText().toString());
                        startActivity(intent);
                        break;
                    case R.id.eight_age:
                        nineAge.setChecked(false);
                        sevenAge.setChecked(false);
                        nothingAge.setChecked(false);
                        setColor();
                        eightAge.setTextColor(Color.WHITE);
                        intent.putExtra("age",eightAge.getText().toString());
                        startActivity(intent);
                        break;
                    case R.id.seven_age:
                        eightAge.setChecked(false);
                        nineAge.setChecked(false);
                        nothingAge.setChecked(false);
                        setColor();
                        sevenAge.setTextColor(Color.WHITE);
                        intent.putExtra("age",sevenAge.getText().toString());
                        startActivity(intent);
                        break;
                    case R.id.noting_age:
                        eightAge.setChecked(false);
                        sevenAge.setChecked(false);
                        nineAge.setChecked(false);
                        setColor();
                        nothingAge.setTextColor(Color.WHITE);
                        intent.putExtra("age",nothingAge.getText().toString());
                        startActivity(intent);
                        break;
                }
            }
        });
        return third;
    }


    private void initView() {
        verticalViewPager = (VerticalViewPager) findViewById(R.id.view_pager);
    }
    private View view;
    private void initListener(){
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verticalViewPager.setCurrentItem(2);
            }
        });
        man.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (views.size()==3){
                        woman.setChecked(false);
                        views.remove(2);
                        View view = initAgePage(Constants.MAN);
                        views.add(view);
                        adapter = new ContentViewAdapter(views);
                        verticalViewPager.setAdapter(adapter);
                        verticalViewPager.setCurrentItem(3);
                        return;
                    }
                        view = initAgePage(Constants.MAN);
                        views.add(view);
                        adapter.notifyDataSetChanged();
                        verticalViewPager.setCurrentItem(3);
                        woman.setChecked(false);


                }
            }
        });
        woman.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                     if (views.size()==3){
                         man.setChecked(false);
                         views.remove(2);
                         View view = initAgePage(Constants.WOMAN);
                         views.add(view);
                         adapter = new ContentViewAdapter(views);
                         verticalViewPager.setAdapter(adapter);
                         verticalViewPager.setCurrentItem(3);
                         return;
                     }
                        view = initAgePage(Constants.WOMAN);
                        views.add(view);
                        adapter.notifyDataSetChanged();
                        verticalViewPager.setCurrentItem(3);
                        man.setChecked(false);
                }
            }
        });


        mJumpNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WelcomeActivity.this, R.string.jump, Toast.LENGTH_SHORT).show();
            }
        });

    }

    //还原默认字体颜色
    private void setColor(){
        nineAge.setTextColor(Color.BLACK);
        eightAge.setTextColor(Color.BLACK);
        sevenAge .setTextColor(Color.BLACK);
        nothingAge.setTextColor(Color.BLACK);
    }



}
