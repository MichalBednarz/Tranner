package com.example.android.tranner.categoryscreen.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.example.android.tranner.categoryscreen.activities.CategoryActivity;
import com.example.android.tranner.categoryscreen.fragments.FragmentFamiliar;
import com.example.android.tranner.categoryscreen.fragments.FragmentNew;
import com.example.android.tranner.data.providers.itemprovider.CategoryItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michał on 2017-04-22.
 */

public class FragmentSlidingAdapter extends FragmentPagerAdapter {

    private SparseArray<WeakReference<Fragment>> mRegisteredFragments = new SparseArray<>();

    final int PAGE_COUNT = 2;
    private String mTabTitles[] = new String[]{"New", "Familiar"};
    private FragmentNew mNewFragment;
    private FragmentFamiliar mFamiliarFragment;


    public FragmentSlidingAdapter(FragmentManager fm) {
        super(fm);
        mNewFragment = FragmentNew.newInstance();
        mFamiliarFragment = FragmentFamiliar.newInstance();
    }

    public void updateNewFragment(List<CategoryItem> itemList) {
        mNewFragment.updateItemList(itemList);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mNewFragment;
            case 1:
                return mFamiliarFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        WeakReference fragmentWeakReference = new WeakReference<>(fragment);
        mRegisteredFragments.put(position, fragmentWeakReference);
        
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mRegisteredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles[position];
    }

    public Fragment getRegisteredFragment(int position) {
        final WeakReference<Fragment> fragmentWeakReference = mRegisteredFragments.get(position);

        if(fragmentWeakReference != null) {
            return fragmentWeakReference.get();
        } else {
            return null;
        }
    }
}
