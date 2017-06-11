package com.example.android.tranner.categoryscreen.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.example.android.tranner.R;
import com.example.android.tranner.categoryscreen.activities.CategoryActivity;
import com.example.android.tranner.categoryscreen.fragments.FragmentFamiliar;
import com.example.android.tranner.categoryscreen.fragments.FragmentNew;

import java.lang.ref.WeakReference;

/**
 * Created by Michał on 2017-04-22.
 */

public class FragmentSlidingAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;
    private String mTabTitles[];
    private int mParentId;

    public FragmentSlidingAdapter(Context context, int parentId) {
        super(((CategoryActivity) context).getSupportFragmentManager());
        this.mTabTitles = context.getResources().getStringArray(R.array.category_tabs);
        this.mParentId = parentId;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FragmentNew.newInstance(mParentId);
            case 1:
                return FragmentFamiliar.newInstance(mParentId);
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
