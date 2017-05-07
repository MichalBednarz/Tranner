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

    //TODO examine fragments usage and maybe come up with another attitude towards fragments update
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

        if (fragmentWeakReference != null) {
            return fragmentWeakReference.get();
        } else {
            return null;
        }
    }
}
