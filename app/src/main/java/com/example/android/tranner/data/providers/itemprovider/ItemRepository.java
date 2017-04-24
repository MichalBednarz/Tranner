package com.example.android.tranner.data.providers.itemprovider;

import com.example.android.tranner.data.Category;
import com.example.android.tranner.data.CategoryItem;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Michał on 2017-04-23.
 */

public class ItemRepository implements ItemContract.Repository {

    @Override
    public Single<List<CategoryItem>> loadItems(Category category) {
        return null;
    }

    @Override
    public Single<Long> addItem(CategoryItem item) {
        return null;
    }

    @Override
    public Single<Integer> deleteItem(CategoryItem item) {
        return null;
    }

    @Override
    public Single<Integer> updateItem(CategoryItem item) {
        return null;
    }
}
