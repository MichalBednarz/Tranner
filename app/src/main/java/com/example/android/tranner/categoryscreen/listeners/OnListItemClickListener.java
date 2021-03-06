package com.example.android.tranner.categoryscreen.listeners;

import com.example.android.tranner.data.providers.itemprovider.CategoryItem;

/**
 * Created by Michał on 2017-04-23.
 */

public interface OnListItemClickListener {
    void onListOpenItem(CategoryItem item);

    void onListDeleteItem(CategoryItem item, int position);

    void onListMoveItem(CategoryItem item);

    void onListUpdateItem(CategoryItem item);
}
