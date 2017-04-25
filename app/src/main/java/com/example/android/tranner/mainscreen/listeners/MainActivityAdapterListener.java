package com.example.android.tranner.mainscreen.listeners;

import com.example.android.tranner.data.providers.categoryprovider.Category;

/**
 * Created by Michał on 2017-04-10.
 */

public interface MainActivityAdapterListener {
    void onCategoryDeleted(Category category);
    void onChangeBackdropClicked(Category category);
}
