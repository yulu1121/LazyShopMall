package com.anshi.lazyshopmall.common;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class ContentFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> mTitles;
    public ContentFragmentAdapter(FragmentManager fm, List<Fragment> fragments,List<String> mTitles) {
        super(fm);
        this.fragments = fragments;
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}