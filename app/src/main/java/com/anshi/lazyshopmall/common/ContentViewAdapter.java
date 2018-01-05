package com.anshi.lazyshopmall.common;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 *
 * Created by yulu on 2018/1/2.
 */

public class ContentViewAdapter extends PagerAdapter {
    private List<View> list;
    public ContentViewAdapter(List<View> mList){
        this.list = mList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }


    @Override
    public int getCount() {
        return null==list?0:list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
