package com.anshi.lazyshopmall.view.fragments.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.anshi.lazyshopmall.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by yulu on 2018/1/18.
 */

public class MainDrawerFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private View mRootView;
    private CommonAdapter<String> commonAdapter;
    private HeaderAndFooterWrapper mHeaderWrap;
    private Context mContext;
    private View mHeaderOne;
    private View mHeaderTwo;
    private View mFooterView;
    private com.zhy.adapter.abslistview.CommonAdapter<String> mHeadOneAdapter;
    private com.zhy.adapter.abslistview.CommonAdapter<String> mHeadTwoAdapter;
    private CommonAdapter<Integer> mFooterAdapter;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }
    public static MainDrawerFragment getInstance(){
        return new MainDrawerFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.main_left_drawer_main,container,false);
        initView(mRootView);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDatas();
    }

    private void initView(View mFootView) {
        mRecyclerView = mFootView.findViewById(R.id.left_drawer_main);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,4));
    }

    private void initDatas(){
       final List<Integer> mHeadGridIcons = new ArrayList<>();
        List<String> mHeadGridTitles = new ArrayList<>();
        mHeadGridIcons.add(R.drawable.pg_grid_happy);
        mHeadGridTitles.add("6.18狂欢");
        mHeadGridIcons.add(R.drawable.pg_grid_dress);
        mHeadGridTitles.add("女装馆");
        mHeadGridIcons.add(R.drawable.pg_grid_bargin);
        mHeadGridTitles.add("聚划算");
        mHeadGridIcons.add(R.drawable.pg_grid_ticket);
        mHeadGridTitles.add("领券中心");
        mHeadGridIcons.add(R.drawable.pg_grid_good);
        mHeadGridTitles.add("wow精选");
        mHeadGridIcons.add(R.drawable.pg_grid_mouth);
        mHeadGridTitles.add("闲城口碑");
        mHeadGridIcons.add(R.drawable.pg_grid_mall);
        mHeadGridTitles.add("活动商城");
        mHeadGridIcons.add(R.drawable.pg_grid_shopmall);
        mHeadGridTitles.add("商圈会场");
        mHeadGridIcons.add(R.drawable.pg_grid_online);
        mHeadGridTitles.add("即将上线");
        mHeadGridIcons.add(R.drawable.pg_grid_more);
        mHeadGridTitles.add("更多分类");
        Log.e("xxx",mHeadGridIcons.size()+"\t"+mHeadGridTitles.size());
        commonAdapter = new CommonAdapter<String>(mContext,R.layout.main_category_grid_item,mHeadGridTitles) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                ImageView image =  holder.getView(R.id.main_image_grid);
                image.setImageResource(mHeadGridIcons.get(position-2));
                TextView textView = holder.getView(R.id.popu_test);
                textView.setText(s);
            }
        };
        initHeaderOne();
        initHeaderTwo();
        initFooterView();
        mHeaderWrap = new HeaderAndFooterWrapper(commonAdapter);
        mHeaderWrap.addHeaderView(mHeaderOne);
        mHeaderWrap.addHeaderView(mHeaderTwo);
        mHeaderWrap.addFootView(mFooterView);
        mRecyclerView.setAdapter(mHeaderWrap);
        mHeaderWrap.notifyDataSetChanged();
    }
    private void initHeaderOneData(){
        final List<Integer> headerOneIcons = new ArrayList<>();
        List<String> headerOneTitles = new ArrayList<>();
        headerOneIcons.add(R.drawable.pg_lazy_shop);
        headerOneTitles.add("闲城首页");
        headerOneIcons.add(R.drawable.pg_grid_shopmall);
        headerOneTitles.add("年中大促");
        headerOneIcons.add(R.drawable.pg_grid_happy);
        headerOneTitles.add("我的专属");
        headerOneIcons.add(R.drawable.pg_grid_dress);
        headerOneTitles.add("服装城");
        mHeadOneAdapter = new com.zhy.adapter.abslistview.CommonAdapter<String>(mContext,R.layout.left_head_one_item,headerOneTitles) {
            @Override
            protected void convert(com.zhy.adapter.abslistview.ViewHolder holder, String s, int position) {
                ImageView imageView = holder.getView(R.id.left_drawer_image);
                imageView.setImageResource(headerOneIcons.get(position));
                TextView textView = holder.getView(R.id.left_drawer_text);
                textView.setText(s);
            }

    };
    mHeadOneAdapter.notifyDataSetChanged();
    }
    private void initHeaderOne(){
        mHeaderOne = LayoutInflater.from(mContext).inflate(R.layout.left_drawer_header_one,null);
        ListView mRecyclerView = mHeaderOne.findViewById(R.id.left_drawer_head_one);
        initHeaderOneData();
        mRecyclerView.setAdapter(mHeadOneAdapter);
    }
    private void initHeaderTwo(){
        mHeaderTwo = LayoutInflater.from(mContext).inflate(R.layout.left_drawer_head_two,null);
        GridView gridView = mHeaderTwo.findViewById(R.id.left_drawer_grid);
        initHeaderTwoData();
        gridView.setAdapter(mHeadTwoAdapter);
    }

    private void initHeaderTwoData(){
        final List<Integer>  mCategoryIcons = new ArrayList<>();
        List<String>  mCategoryNames = new ArrayList<>();
        mCategoryIcons.add(R.drawable.pg_main_heart);
        mCategoryNames.add("我的专属");
        mCategoryIcons.add(R.drawable.pg_main_baby);
        mCategoryNames.add("母婴");
        mCategoryIcons.add(R.drawable.pg_main_car);
        mCategoryNames.add("整车车品");
        mCategoryIcons.add(R.drawable.pg_main_womandress);
        mCategoryNames.add("女装");
        mCategoryIcons.add(R.drawable.pg_main_mandress);
        mCategoryNames.add("男装");
        mCategoryIcons.add(R.drawable.pg_main_shoes);
        mCategoryNames.add("鞋靴");
        mCategoryIcons.add(R.drawable.pg_main_box);
        mCategoryNames.add("箱包");
        mCategoryIcons.add(R.drawable.pg_main_phone_camera);
        mCategoryNames.add("手机数码");
        mHeadTwoAdapter = new com.zhy.adapter.abslistview.CommonAdapter<String>(mContext,R.layout.grid_item,mCategoryNames) {
            @Override
            protected void convert(com.zhy.adapter.abslistview.ViewHolder viewHolder, String item, int position) {
                ImageView image = viewHolder.getView(R.id.main_image_grid);
                image.setImageResource(mCategoryIcons.get(position));
                TextView textView = viewHolder.getView(R.id.popu_test);
                textView.setText(item);
            }
        };
        mHeadTwoAdapter.notifyDataSetChanged();
    }

    private void initFootData(){
        List<Integer> icons = new ArrayList<>();
        icons.add(R.drawable.pg_main_electrator);
        icons.add(R.drawable.pg_main_box);
        icons.add(R.drawable.pg_main_diamons);
        icons.add(R.drawable.pg_main_makeup);
        icons.add(R.drawable.pg_main_goods);
        mFooterAdapter = new CommonAdapter<Integer>(mContext,R.layout.grid_item,icons) {
            @Override
            protected void convert(ViewHolder holder, Integer integer, int position) {
                ImageView image = holder.getView(R.id.main_image_grid);
                image.setImageResource(integer);
                TextView textView = holder.getView(R.id.popu_test);
                textView.setVisibility(View.GONE);
            }
        };
        mFooterAdapter.notifyDataSetChanged();
    }

    private void initFooterView(){
        mFooterView = LayoutInflater.from(mContext).inflate(R.layout.left_drawer_footer,null);
        RecyclerView footerRecycler  = mFooterView.findViewById(R.id.left_drawer_footer_recycler);
        footerRecycler.setLayoutManager(new GridLayoutManager(mContext,5));
        initFootData();
        footerRecycler.setAdapter(mFooterAdapter);
    }

}
