package com.example.android.tranner.categoryscreen.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.example.android.tranner.categoryscreen.fragments.FragmentFamiliar;
import com.example.android.tranner.categoryscreen.fragments.FragmentNew;

import java.lang.ref.WeakReference;

/**
 * Created by Michał on 2017-04-22.
 */

public class FragmentSlidingAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;
    private SparseArray<WeakReference<Fragment>> mRegisteredFragments = new SparseArray<>();
    private String mTabTitles[] = new String[]{"New", "Familiar"};
    private int mParentId;

    public FragmentSlidingAdapter(FragmentManager fm, int parentId) {
        super(fm);
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
