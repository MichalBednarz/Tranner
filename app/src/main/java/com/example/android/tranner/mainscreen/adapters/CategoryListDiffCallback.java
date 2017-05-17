package com.example.android.tranner.mainscreen.adapters;

import android.support.v7.util.DiffUtil;


import com.example.android.tranner.data.providers.categoryprovider.Category;

import java.util.List;

/**
 * Created by Michał on 2017-05-17.
 */

public class CategoryListDiffCallback extends DiffUtil.Callback {
    private List<Category> mCurrent;
    private List<Category> mNext;

    public CategoryListDiffCallback(List<Category> current, List<Category> next) {
        this.mCurrent = current;
        this.mNext = next;
    }

    @Override
    public int getOldListSize() {
        return mCurrent.size();
    }

    @Override
    public int getNewListSize() {
        return mNext.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {

        return mCurrent.get(oldItemPosition).getId() == mNext.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

        return mCurrent.get(oldItemPosition).equals(mNext.get(newItemPosition));
    }
}
