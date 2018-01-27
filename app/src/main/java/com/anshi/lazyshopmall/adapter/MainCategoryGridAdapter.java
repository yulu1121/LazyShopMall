package com.anshi.lazyshopmall.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anshi.lazyshopmall.R;
import com.anshi.lazyshopmall.view.activity.goodsdetail.GoodsDetailActivity;

import java.util.List;

/**
 *
 * Created by yulu on 2018/1/15.
 */

public class MainCategoryGridAdapter extends BaseAdapter {
    private List<String> mDatas;
    private Context mContext;
    public MainCategoryGridAdapter(Context context,List<String> data){
        this.mContext = context;
        this.mDatas = data;
    }
    @Override
    public int getCount() {
        return null==mDatas?0:mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        GridViewHolder gridViewHolder;
        if (null==view){
            view = LayoutInflater.from(mContext).inflate(R.layout.main_item_recycler_item,parent,false);
            gridViewHolder = new GridViewHolder(view);
        }else {
            gridViewHolder = (GridViewHolder) view.getTag();
        }
        if (position%2!=0){
            gridViewHolder.mLinearLayout.setVisibility(View.INVISIBLE);
            gridViewHolder.mGoodsDesc.setVisibility(View.INVISIBLE);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                mContext.startActivity(intent);
            }
        });
        gridViewHolder.mGoodsName.setText(mDatas.get(position));
        return view;
    }

    private class GridViewHolder{
        TextView mGoodsName;
        ImageView mGoodsIcon;
        TextView mGoodsDesc;
        ImageView mGoodsImage;
        LinearLayout mLinearLayout;
        GridViewHolder (View view){
            mGoodsName = view.findViewById(R.id.main_category_item_goods);
            mGoodsIcon = view.findViewById(R.id.main_item_recycler_img);
            mGoodsDesc = view.findViewById(R.id.item_des);
            mGoodsImage = view.findViewById(R.id.main_category_item_image);
            mLinearLayout = view.findViewById(R.id.item_has_image_layout);
            view.setTag(this);
        }
    }
}
