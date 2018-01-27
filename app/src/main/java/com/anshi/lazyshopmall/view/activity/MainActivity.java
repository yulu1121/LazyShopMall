package com.anshi.lazyshopmall.view.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.anshi.lazyshopmall.R;
import com.anshi.lazyshopmall.adapter.MainCategoryGridAdapter;
import com.anshi.lazyshopmall.common.BaseActivity;
import com.anshi.lazyshopmall.self_view.CanScrollGridView;
import com.anshi.lazyshopmall.self_view.CanScrollSliderLayout;
import com.anshi.lazyshopmall.self_view.HomeGridView;
import com.anshi.lazyshopmall.utils.SharedPreferenceUtils;
import com.anshi.lazyshopmall.view.activity.city.MeituanSelectCityActivity;
import com.anshi.lazyshopmall.view.fragments.main.MainDrawerFragment;
import com.camnter.easycountdowntextureview.EasyCountDownTextureView;
import com.gongwen.marqueen.MarqueeView;
import com.gongwen.marqueen.SimpleMF;
import com.gongwen.marqueen.util.OnItemClickListener;
import com.liuting.sliderlayout.SliderLayout;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static com.anshi.lazyshopmall.utils.ToastUtils.showShortToast;


public class MainActivity extends BaseActivity implements View.OnClickListener{
    private RecyclerView recyclerView;
    private List<String> mDatas;
    private DrawerLayout mDrawerLayout;
    private PopupWindow popupWindow;
    private int index = 0;
    private View rootPopuLayout;
    private List<Integer> mCategoryIcons;
    private List<String> mCategoryNames;
    private RadioButton mMore;
    private View mHeadView;
    private CanScrollSliderLayout mBanner;
    private List<Object> mBannerList;
    private CommonAdapter<String> commonAdapter;
    private HeaderAndFooterWrapper mHeaderWrap;
    private RadioButton mCityRb;
    private HomeGridView mHeadGridView;
    private List<Integer> mHeadGridIcons;
    private List<String> mHeadGridTitles;
    private List<Integer> mSecondGridIcons;
    private View mHeadGridMain;
    private View mPaperHeader;
    private View mSecondHeader;
    private RecyclerView mSecondRecycler;
    private MarqueeView<TextView,String> mGoodFoodMarquee;
    private MarqueeView<TextView, String> mHotMarquee;
    private RecyclerView mCategoryRecycler;
    private EasyCountDownTextureView mSecondText;
    private Map<String,List<String>> mMainPaperMap;
    private ImageView mShopIcon;
    //定义一个变量，来标识是否退出
    private  boolean isExit = false;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    isExit = false;
                    break;
            }
        }
    };
    private LinearLayout mExpandLayout;
    private RadioGroup mRadioGroup;
    private RadioButton mReduceRb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDatas();
        addEventListener();
    }

    /**
     * 设置假的数据
     */
    private void initDatas() {
        mDatas = new ArrayList<>();
        for (int i = 0; i <8; i++) {
            mDatas.add("测试"+i);
        }
        mMainPaperMap = new LinkedHashMap<>();
        mMainPaperMap.put("实惠好货",mDatas);
        mMainPaperMap.put("每日好店",mDatas);
        mMainPaperMap.put("时尚大咖",mDatas);
        mMainPaperMap.put("闲城健康",mDatas);
        Set<String> strings = mMainPaperMap.keySet();
        List<String> keys = new ArrayList<>();
        for (String s:strings) {
            keys.add(s);
        }
        commonAdapter = new CommonAdapter<String>(this,R.layout.main_recycler_view_item, keys) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                LinearLayout linearLayout = holder.getView(R.id.recycler_category_back);
                TextView textView = holder.getView(R.id. main_recycler_item_img_title);
                textView.setText("#"+s);
               switch (s){
                   case "实惠好货":
                       linearLayout.setBackgroundResource(R.drawable.recyler_category_back);
                       textView.setBackgroundResource(R.drawable.main_recycler_item_imge_back);
                       break;
                   case "每日好店":
                       linearLayout.setBackgroundResource(R.drawable.main_recycler_view_item_two);
                       textView.setBackgroundResource(R.drawable.main_recycler_item_image_back_two);
                       break;
                   case "时尚大咖":
                       linearLayout.setBackgroundResource(R.drawable.main_recycler_view_item_three);
                       textView.setBackgroundResource(R.drawable.main_recycler_item_image_back_three);
                       break;
                   case "闲城健康":
                       linearLayout.setBackgroundResource(R.drawable.main_recycler_view_item_four);
                       textView.setBackgroundResource(R.drawable.main_recycler_item_image_back_four);
                       break;
               }
               TextView mTextView = holder.getView(R.id.recycler_category_title);
               mTextView.setText(s);
               HomeGridView gridView = holder.getView(R.id.main_recycler_item_recycler);
                mDatas = new ArrayList<>();
                for (int i = 0; i <8; i++) {
                    mDatas.add("测试"+i);
                }
               gridView.setAdapter(new MainCategoryGridAdapter(MainActivity.this,mDatas));
            }
        };
        initBannerList();
        initGridData();
        initPaperList();
        initSecondList();
        mHeadGridView.setAdapter(new com.zhy.adapter.abslistview.CommonAdapter<String>(this,R.layout.main_category_grid_item,mHeadGridTitles) {
            @Override
            protected void convert(com.zhy.adapter.abslistview.ViewHolder viewHolder, String item, int position) {
                TextView textView =  viewHolder.getView(R.id.popu_test);
                ImageView imageView = viewHolder.getView(R.id.main_image_grid);
                textView.setText(item);
                imageView.setImageResource(mHeadGridIcons.get(position));
            }
        });
        mBanner.setList(mBannerList);
        mBanner.setPictureIndex(0);
        mHeaderWrap = new HeaderAndFooterWrapper(commonAdapter);
        mHeaderWrap.addHeaderView(mHeadView);
        mHeaderWrap.addHeaderView(mHeadGridMain);
        mHeaderWrap.addHeaderView(mPaperHeader);
        mHeaderWrap.addHeaderView(mSecondHeader);
        recyclerView.setAdapter(mHeaderWrap);
        mHeaderWrap.notifyDataSetChanged();

    }
    private void initGridData(){
        mHeadGridIcons = new ArrayList<>();
        mHeadGridTitles = new ArrayList<>();
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
    }

    //测试：初始化banner数据
    private void initBannerList(){
        mBannerList = new ArrayList<>();
        mBannerList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1515577150560&di=57fbb762471cf6e592eda8609094a8f2&imgtype=0&src=http%3A%2F%2Fimgq.duitang.com%2Fuploads%2Fitem%2F201506%2F02%2F20150602193800_H4hUV.jpeg");
        mBannerList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1516172500&di=cfc071ac3dd02978b98dedef4f505e3b&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01c2895542c7790000019ae9df5d62.jpg");
        mBannerList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1515577809241&di=31ed34a9cf9b50405868387955aad005&imgtype=0&src=http%3A%2F%2Fpic2.ooopic.com%2F10%2F78%2F35%2F95b1OOOPICcd.jpg");
        mBannerList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1515577833108&di=39f645bbd2d8fc291b0d494eb5faf7a2&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0142b65832a5f9a8012060c8d86eea.jpg");
        mBannerList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1515577849275&di=80fc65d0409ec11564a3d7c8a611fecf&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0130495806ba44a84a0d304f2b3a10.png");
    }

    //初始化快报界面
    private void initPaperList(){
        List<String> goodFoodPaperList = new ArrayList<>();
        goodFoodPaperList.add("喝普洱茶会发出汗，这是为什么");
        goodFoodPaperList.add("西红柿的做法有很多种，你知道吗");
        SimpleMF<String> marqueeFactory = new SimpleMF<>(this);
        marqueeFactory.setData(goodFoodPaperList);
        mGoodFoodMarquee.setMarqueeFactory(marqueeFactory);
        List<String> hotList = new ArrayList<>();
        hotList.add("驾驶分数不是一年一清，我们算错");
        hotList.add("教师阻拦高铁出发，处罚错了吗");
        SimpleMF<String> stringSimpleMF = new SimpleMF<>(this);
        stringSimpleMF.setData(hotList);
        mHotMarquee.setMarqueeFactory(stringSimpleMF);
    }

    //初始化秒杀界面
    private void initSecondList(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String end ="2018-01-30 18:00:00";
//得到毫秒数
        long timeStart;
        long dayCount = 0;
        try {
            timeStart = System.currentTimeMillis();
            long timeEnd =sdf.parse(end).getTime();
            dayCount = timeEnd - timeStart;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mSecondText.setTime(dayCount);
        mSecondGridIcons = new ArrayList<>();
        mSecondGridIcons.add(R.drawable.pg_steller_bgone);
        mSecondGridIcons.add(R.drawable.pg_steller_bgtwo);
        mSecondGridIcons.add(R.drawable.pg_steller_bgthree);
        mSecondGridIcons.add(R.drawable.pg_steller_bgfour);
        mSecondGridIcons.add(R.drawable.pg_steller_bgfive);
        mSecondRecycler.setLayoutManager(new GridLayoutManager(this,5));
        mSecondRecycler.setAdapter(new CommonAdapter<Integer>(this,R.layout.main_second_recycler_item,mSecondGridIcons) {
            @Override
            protected void convert(ViewHolder holder, Integer res, int position) {
                ImageView imageView = holder.getView(R.id.second_grid_item);
                imageView.setImageResource(res);
                TextView oldPrice = holder.getView(R.id.old_price);
                TextView newPrice = holder.getView(R.id.really_price);
                oldPrice.setText(R.string.test_old_price);
                String price = getString(R.string.new_price);
                SpannableString spannableString = new SpannableString(price);
                spannableString.setSpan(new StrikethroughSpan(),0,price.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                newPrice.setText(spannableString);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        mGoodFoodMarquee.startFlipping();
        mHotMarquee.startFlipping();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoodFoodMarquee.stopFlipping();
        mHotMarquee.stopFlipping();
        mSecondText.stop();
    }

    /**
     * 初始化View
     */
    private void initView() {
        mExpandLayout = (LinearLayout) findViewById(R.id.main_expand_layout);
        mRadioGroup = (RadioGroup) findViewById(R.id.main_tab_group);
        mReduceRb = (RadioButton) findViewById(R.id.main_reduce_rb);
        mHeadView = LayoutInflater.from(this).inflate(R.layout.main_recycler_header,null);
        mHeadGridMain = LayoutInflater.from(this).inflate(R.layout.main_head_grid_main,null);
        mPaperHeader = LayoutInflater.from(this).inflate(R.layout.main_paper_header,null);
        mSecondHeader = LayoutInflater.from(this).inflate(R.layout.main_second_layout,null);
        mBanner = mHeadView.findViewById(R.id.main_layout_banner);
        mHeadGridView =mHeadGridMain.findViewById(R.id.main_head_grid_recycler);
        mGoodFoodMarquee =  mPaperHeader.findViewById(R.id.main_good_food_paper_marquee);
        mHotMarquee =  mPaperHeader.findViewById(R.id.main_paper_hot_marquee);
        mSecondRecycler = mSecondHeader.findViewById(R.id.main_second_recycler);
        mSecondText = mSecondHeader.findViewById(R.id.second_text);
        recyclerView = (RecyclerView) findViewById(R.id.personal_frag_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mDrawerLayout = (DrawerLayout) findViewById(R.id.layout);
        getSupportFragmentManager().beginTransaction().add(R.id.main_frame_layout, MainDrawerFragment.getInstance()).commit();
        View topView = findViewById(R.id.main_toolbar_layout);
        mCityRb = topView.findViewById(R.id.main_local_city_text);
        mShopIcon = topView.findViewById(R.id.main_shop_img);
        String city = SharedPreferenceUtils.getString(this, "city");
        mCityRb.setText(city);
        View view = findViewById(R.id.main_category);
        mMore = view.findViewById(R.id.check_rb);
        mCategoryRecycler = view.findViewById(R.id.main_category_grid_recycler);
        initPopupGridData();
        initPopupLayout();
        initPopuWindow();
    }

    /**
     * 初始化popupWindow内的数据
     */
    private void initPopupGridData(){
       mCategoryIcons = new ArrayList<>();
       mCategoryNames = new ArrayList<>();
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
       mCategoryIcons.add(R.drawable.pg_main_innerclothese);
       mCategoryNames.add("内衣");
       mCategoryIcons.add(R.drawable.pg_main_phone_camera);
       mCategoryNames.add("手机数码");
       mCategoryIcons.add(R.drawable.pg_main_goods);
       mCategoryNames.add("百货");
       mCategoryIcons.add(R.drawable.pg_main_diamons);
       mCategoryNames.add("珠宝配饰");
       mCategoryIcons.add(R.drawable.pg_main_outdoor);
       mCategoryNames.add("运动户外");
       mCategoryIcons.add(R.drawable.pg_main_electrator);
       mCategoryNames.add("家电");
       mCategoryIcons.add(R.drawable.pg_main_food);
       mCategoryNames.add("食品");
       mCategoryIcons.add(R.drawable.pg_main_makeup);
       mCategoryNames.add("美妆");
    }
    //初始化PopupWindow的content
    private void initPopupLayout(){
        rootPopuLayout = LayoutInflater.from(this).inflate(R.layout.main_popu_layout,null);
        mCategoryRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mCategoryRecycler.setAdapter(new CommonAdapter<String>(this,R.layout.category_grid_layout,mCategoryNames) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                TextView textView =  holder.getView(R.id.popu_test);
                ImageView imageView = holder.getView(R.id.main_image_grid);
                textView.setText(s);
                imageView.setImageResource(mCategoryIcons.get(position));
            }

        });
        CanScrollGridView gridRecyclerView = rootPopuLayout.findViewById(R.id.main_grid);
        gridRecyclerView.setAdapter(new com.zhy.adapter.abslistview.CommonAdapter<String>(this,R.layout.grid_item,mCategoryNames) {
            @Override
            protected void convert(com.zhy.adapter.abslistview.ViewHolder viewHolder, String item, int position) {
                TextView textView =  viewHolder.getView(R.id.popu_test);
                ImageView imageView = viewHolder.getView(R.id.main_image_grid);
                textView.setText(item);
                imageView.setImageResource(mCategoryIcons.get(position));
            }
        });

    }

    /**
     * 添加控件的点击监听事件
     */
    private void addEventListener(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof LinearLayoutManager) {
                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                        int position = linearLayoutManager.findFirstVisibleItemPosition();

                        if (position==0){
                            onScrollDown();
                        }else {
                            onScrollUp();
                        }
                    }

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//
//                    if (disY>25&&isShow){
//                        isShow = false;
//                        onScrollUp();
//                        disY = 0;
//                    }
//                    if (disY<-25&&!isShow){
//                        isShow = true;
//                        onScrollDown();
//                        disY = 0;
//                    }
//
//
//
//                if ((isShow&&dy>0)||(!isShow&&dy<0)){
//                    disY+=dy;
//                }

            }
        });

        mMore.setOnClickListener(this);
        mCityRb.setOnClickListener(this);
        mExpandLayout.setOnClickListener(this);
        mReduceRb.setOnClickListener(this);
        mShopIcon.setOnClickListener(this);
        mBanner.setListener(new SliderLayout.IOnClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showShortToast(MainActivity.this,"banner"+position);
            }
        });

        mHotMarquee.setOnItemClickListener(new OnItemClickListener<TextView,String>() {
            @Override
            public void onItemClickListener(TextView mView, String mData, int mPosition) {
                showShortToast(MainActivity.this,""+mPosition);
            }
        });
        mGoodFoodMarquee.setOnItemClickListener(new OnItemClickListener<TextView,String>() {
            @Override
            public void onItemClickListener(TextView mView, String mData, int mPosition) {
                showShortToast(MainActivity.this, "" + mPosition);
            }
        });

    }
    /**
     * 下滑监听
     */
    private void onScrollDown() {
//下滑时要执行的代码
       if (popupWindow.isShowing()){
           popupWindow.dismiss();
           findViewById(R.id.main_category_grid_recycler).setVisibility(View.VISIBLE);
           findViewById(R.id.all_text).setVisibility(View.GONE);
           mMore.setChecked(false);
           index = 0;
       }
       findViewById(R.id.goods_category_layout).setVisibility(View.VISIBLE);
    }

    /**
     * 上滑监听
     */
    private void onScrollUp() {
//上滑时要执行的代码
        if (popupWindow.isShowing()){
            popupWindow.dismiss();
            findViewById(R.id.main_category_grid_recycler).setVisibility(View.VISIBLE);
            findViewById(R.id.all_text).setVisibility(View.GONE);
            mMore.setChecked(false);
            index = 0;
        }
        findViewById(R.id.goods_category_layout).setVisibility(View.GONE);
    }

    /**
     * 初始化popupWindow
     */
    private void initPopuWindow() {

        popupWindow = new PopupWindow(rootPopuLayout, WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.popu_back));
    }
    public static final int CITY_REQUEST_CODE = 0;

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.check_rb:
                index++;
                if (index==1){
                    mMore.setChecked(true);
                    findViewById(R.id.main_category_grid_recycler).setVisibility(View.INVISIBLE);
                    findViewById(R.id.all_text).setVisibility(View.VISIBLE);
                    popupWindow.showAsDropDown(mMore);
                }else {
                    mMore.setChecked(false);
                    popupWindow.dismiss();
                    findViewById(R.id.main_category_grid_recycler).setVisibility(View.VISIBLE);
                    findViewById(R.id.all_text).setVisibility(View.GONE);
                    index = 0;
                }
                break;
            case R.id.main_local_city_text:
                Intent intent = new Intent(this, MeituanSelectCityActivity.class);
                intent.putExtra("localCity",SharedPreferenceUtils.getString(this,"city"));
                startActivityForResult(intent,CITY_REQUEST_CODE);
                break;
            case R.id.main_expand_layout:
                expand();
                break;
            case R.id.main_reduce_rb:
                reduce();
                break;
            case R.id.main_shop_img:
                mDrawerLayout.openDrawer(Gravity.START);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK&&data!=null){
            switch (requestCode){
                case CITY_REQUEST_CODE:
                    String chooseCity = data.getStringExtra("chooseCity");
                    mCityRb.setText(chooseCity);
                    break;
            }
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);

    }

    /**
     * 退出应用程序
     */
    public void exit(){
        if (!isExit) {
            isExit = true;
            Toast.makeText(this,"再按一次退出闲城",Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finishAffinity();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBanner.stopAutoPlay();
        if (popupWindow.isShowing()){
            popupWindow.dismiss();
        }
    }

    private void expand() {
        //设置伸展状态时的布局
        mExpandLayout.setVisibility(View.GONE);
        mRadioGroup.setVisibility(View.VISIBLE);
    }

    private void reduce() {
        //设置收缩状态时的布局
        mExpandLayout.setVisibility(View.VISIBLE);
        mRadioGroup.setVisibility(View.GONE);
    }

}
