package com.anshi.lazyshopmall.view.activity.city;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.anshi.lazyshopmall.R;
import com.anshi.lazyshopmall.adapter.MeituanAdapter;
import com.anshi.lazyshopmall.common.BaseActivity;
import com.anshi.lazyshopmall.decoration.DividerItemDecoration;
import com.anshi.lazyshopmall.enty.AllChinaCities;
import com.anshi.lazyshopmall.enty.MeiTuanBean;
import com.anshi.lazyshopmall.enty.MeituanHeaderBean;
import com.anshi.lazyshopmall.enty.MeituanTopHeaderBean;
import com.anshi.lazyshopmall.utils.CommonAdapter;
import com.anshi.lazyshopmall.utils.HeaderRecyclerAndFooterWrapperAdapter;
import com.anshi.lazyshopmall.utils.ViewHolder;
import com.google.gson.Gson;
import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;
import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

;


/**
 * 介绍： 高仿美团选择城市页面
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/11/7.
 */
public class MeituanSelectCityActivity extends BaseActivity {
    private static final String TAG = "zxt";
    private Context mContext;
    private RecyclerView mRv;
    private MeituanAdapter mAdapter;
    private HeaderRecyclerAndFooterWrapperAdapter mHeaderAdapter;
    private LinearLayoutManager mManager;
    private List<AllChinaCities.CitylistBean.CBean> cities;

    //设置给InexBar、ItemDecoration的完整数据集
    private List<BaseIndexPinyinBean> mSourceDatas;
    //头部数据源
    private List<MeituanHeaderBean> mHeaderDatas;
    //主体部分数据源（城市数据）
    private List<MeiTuanBean> mBodyDatas;

    private SuspensionDecoration mDecoration;

    /**
     * 右侧边栏导航区域
     */
    private IndexBar mIndexBar;

    /**
     * 显示指示器DialogText
     */
    private TextView mTvSideBarHint;
    private String localCity;
    private View mToolbarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meituan);
        mContext = this;
        initCitiesAndProvince();

        localCity = getIntent().getStringExtra("localCity");
        initToolBar();
        mRv = (RecyclerView) findViewById(R.id.rv);
        mRv.setLayoutManager(mManager = new LinearLayoutManager(this));
        mSourceDatas = new ArrayList<>();
        mHeaderDatas = new ArrayList<>();
        List<String> locationCity = new ArrayList<>();
        locationCity.add("定位中");
        mHeaderDatas.add(new MeituanHeaderBean(locationCity, "定位城市", "定"));
        List<String> hotCitys = new ArrayList<>();
        mHeaderDatas.add(new MeituanHeaderBean(hotCitys, "热门城市", "热"));
        mSourceDatas.addAll(mHeaderDatas);

        mAdapter = new MeituanAdapter(this, R.layout.meituan_item_select_city, mBodyDatas){
            @Override
            public void convert(ViewHolder holder, final MeiTuanBean cityBean) {
                super.convert(holder, cityBean);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra("chooseCity",cityBean.getCity());
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                });
            }
        };
        mHeaderAdapter = new HeaderRecyclerAndFooterWrapperAdapter(mAdapter) {
            @Override
            protected void onBindHeaderHolder(ViewHolder holder, int headerPos, int layoutId, Object o) {
                switch (layoutId) {
                    case R.layout.meituan_item_header:
                        final MeituanHeaderBean meituanHeaderBean = (MeituanHeaderBean) o;
                        //网格
                        RecyclerView recyclerView = holder.getView(R.id.rvCity);
                        recyclerView.setAdapter(
                                new CommonAdapter<String>(mContext, R.layout.meituan_item_header_item, meituanHeaderBean.getCityList()) {
                                    @Override
                                    public void convert(ViewHolder holder, final String cityName) {
                                        holder.setText(R.id.tvName, cityName);
                                        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent();
                                                intent.putExtra("chooseCity",cityName);
                                                setResult(RESULT_OK,intent);
                                                finish();
                                            }
                                        });
                                    }
                                });
                        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
                        break;
                    case R.layout.meituan_item_header_top:
                        MeituanTopHeaderBean meituanTopHeaderBean = (MeituanTopHeaderBean) o;
                        holder.setText(R.id.tvCurrent, meituanTopHeaderBean.getTxt());
                        break;
                    default:
                        break;
                }
            }
        };
        mHeaderAdapter.setHeaderView(0, R.layout.meituan_item_header, mHeaderDatas.get(0));
        mHeaderAdapter.setHeaderView(1, R.layout.meituan_item_header, mHeaderDatas.get(1));

        mRv.setAdapter(mHeaderAdapter);
        mRv.addItemDecoration(mDecoration = new SuspensionDecoration(this, mSourceDatas)
                .setmTitleHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, getResources().getDisplayMetrics()))
                .setColorTitleBg(0xffefefef)
                .setTitleFontSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()))
                .setColorTitleFont(mContext.getResources().getColor(android.R.color.black))
                .setHeaderViewCount(mHeaderAdapter.getHeaderViewCount() - mHeaderDatas.size()));
        mRv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));

        //使用indexBar
        mTvSideBarHint = (TextView) findViewById(R.id.tvSideBarHint);//HintTextView
        mIndexBar = (IndexBar) findViewById(R.id.indexBar);//IndexBar

        mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager)//设置RecyclerView的LayoutManager
                .setHeaderViewCount(mHeaderAdapter.getHeaderViewCount() - mHeaderDatas.size());


        initDatas(cities);
    }

    /**
     * 组织数据源
     *
     * */
    private void initDatas(final List<AllChinaCities.CitylistBean.CBean> mList) {
        //延迟两秒 模拟加载数据中....
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBodyDatas = new ArrayList<>();
                for (int i = 0; i < mList.size(); i++) {
                    MeiTuanBean cityBean = new MeiTuanBean();
                    cityBean.setCity(mList.get(i).getN());//设置城市名称
                    mBodyDatas.add(cityBean);
                }
                //先排序
                mIndexBar.getDataHelper().sortSourceDatas(mBodyDatas);

                mAdapter.setDatas(mBodyDatas);
                mHeaderAdapter.notifyDataSetChanged();
                mSourceDatas.addAll(mBodyDatas);

                mIndexBar.setmSourceDatas(mSourceDatas)//设置数据
                        .invalidate();
                mDecoration.setmDatas(mSourceDatas);
            }
        }, 1000);

        //延迟两秒加载头部
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                MeituanHeaderBean header1 = mHeaderDatas.get(0);
                header1.getCityList().clear();
                header1.getCityList().add(localCity);

                MeituanHeaderBean header3 = mHeaderDatas.get(1);
                List<String> hotCitys = new ArrayList<>();
                hotCitys.add("上海");
                hotCitys.add("北京");
                hotCitys.add("杭州");
                hotCitys.add("广州");
                header3.setCityList(hotCitys);

                mHeaderAdapter.notifyItemRangeChanged(1, 2);

            }
        }, 2000);

    }

    private String getJson(String fileName) {
        // TODO Auto-generated method stub
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    getAssets().open(fileName)));
            String next = "";
            while (null != (next = br.readLine())) {
                sb.append(next);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            sb.delete(0, sb.length());
        }
        return sb.toString().trim();
    }

    private void initCitiesAndProvince() {
        cities = new ArrayList<>();

        String json = getJson("cities.json");
        Gson gson = new Gson();
        AllChinaCities allChinaCities = gson.fromJson(json, AllChinaCities.class);
        List<AllChinaCities.CitylistBean> citylist = allChinaCities.getCitylist();
        for (int i = 0; i < citylist.size(); i++) {
            List<AllChinaCities.CitylistBean.CBean> c = citylist.get(i).getC();
            for (int j = 0;j<c.size();j++){
                cities.add(c.get(j));
            }

        }
    }
    public void back(View view){
        finish();
    }

//    /**
//     * 更新数据源
//     *
//     * @param view
//     */
//    public void updateDatas(View view) {
//        for (int i = 0; i < 5; i++) {
//            mBodyDatas.add(new MeiTuanBean("东京"));
//            mBodyDatas.add(new MeiTuanBean("大阪"));
//        }
//        //先排序
//        mIndexBar.getDataHelper().sortSourceDatas(mBodyDatas);
//        mSourceDatas.clear();
//        mSourceDatas.addAll(mHeaderDatas);
//        mSourceDatas.addAll(mBodyDatas);
//
//        mHeaderAdapter.notifyDataSetChanged();
//        mIndexBar.invalidate();
//    }

    private void initToolBar(){
        mToolbarView = findViewById(R.id.city_choose_toolbar);
        mToolbarView.findViewById(R.id.common_jump_next).setVisibility(View.GONE);
        TextView mToolBarText = mToolbarView.findViewById(R.id.toolbar_text);
        mToolBarText.setText("城市选择");
        mToolbarView.findViewById(R.id.city_text).setVisibility(View.GONE);
    }
}
