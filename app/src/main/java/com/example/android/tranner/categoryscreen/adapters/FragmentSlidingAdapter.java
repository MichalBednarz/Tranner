package com.example.android.tranner.categoryscreen.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.tranner.categoryscreen.activities.CategoryActivity;
import com.example.android.tranner.categoryscreen.fragments.FragmentFamiliar;
import com.example.android.tranner.categoryscreen.fragments.FragmentNew;

/**
 * Created by Michał on 2017-04-22.
 */

public class FragmentSlidingAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;
    private String mTabTitles[] = new String[] { "New", "Familiar" };

    private CategoryActivity mActivity;

    public FragmentSlidingAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mActivity = (CategoryActivity) context;

    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return mActivity.getFragmentNew();
            case 1:
                return mActivity.getFragmentFamiliar();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles[position];
    }
}
