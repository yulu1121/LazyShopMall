package com.anshi.lazyshopmall.adapter;

import android.content.Context;


import com.anshi.lazyshopmall.R;
import com.anshi.lazyshopmall.enty.MeiTuanBean;
import com.anshi.lazyshopmall.utils.CommonAdapter;
import com.anshi.lazyshopmall.utils.ViewHolder;

import java.util.List;


/**
 * Created by zhangxutong .
 * Date: 16/08/28
 */

public class MeituanAdapter extends CommonAdapter<MeiTuanBean> {
    public MeituanAdapter(Context context, int layoutId, List<MeiTuanBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, final MeiTuanBean cityBean) {
        holder.setText(R.id.tvCity, cityBean.getCity());

    }
}