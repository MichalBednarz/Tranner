package com.example.android.tranner.data.providers.itemprovider;

import com.example.android.tranner.data.providers.CategoryDatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

import static com.example.android.tranner.data.ConstantKeys.*;
import static com.example.android.tranner.data.providers.CategoryDatabaseContract.*;

/**
 * Created by Michał on 2017-04-27.
 */

public class ItemRepository implements ItemContract.Repository {

    private CategoryDatabaseHelper mDatabaseHelper;
    private Dao<CategoryItem, Integer> mItemDao = null;

    public ItemRepository(CategoryDatabaseHelper categoryDatabaseHelper) {
        this.mDatabaseHelper = categoryDatabaseHelper;
    }

    @Override
    public Single<List<CategoryItem>> loadNewItems(int parentCategory) {
        List<CategoryItem> categoryList = new ArrayList<>();
        try {
            mItemDao = mDatabaseHelper.getItemDao();
            categoryList = mItemDao.queryBuilder()
                    .where()
                    .eq(ItemEntry.ITEM_PARENT_CATEGORY, parentCategory)
                    .and()
                    .eq(ItemEntry.ITEM_TAB, ITEM_TAB_NEW)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Single.just(categoryList);
    }

    @Override
    public Single<List<CategoryItem>> loadFamiliarItems(int parentCategory) {
        List<CategoryItem> categoryList = new ArrayList<>();
        try {
            mItemDao = mDatabaseHelper.getItemDao();
            categoryList = mItemDao.queryBuilder()
                    .where()
                    .eq(ItemEntry.ITEM_PARENT_CATEGORY, parentCategory)
                    .and()
                    .eq(ItemEntry.ITEM_TAB, ITEM_TAB_FAMILIAR)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Single.just(categoryList);
    }

    @Override
    public Single<Long> addItem(CategoryItem item) {
        long rowNum = 0;
        try {
            mItemDao = mDatabaseHelper.getItemDao();
            rowNum = mItemDao.create(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Single.just(rowNum);
    }

    @Override
    public Single<Integer> deleteItem(CategoryItem item) {
        int rowNum = 0;
        try {
            mItemDao = mDatabaseHelper.getItemDao();
            rowNum = mItemDao.delete(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Single.just(rowNum);
    }

    @Override
    public Single<Integer> updateItem(CategoryItem item) {
        int rowNum = 0;
        try {
            mItemDao = mDatabaseHelper.getItemDao();
            rowNum = mItemDao.update(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Single.just(rowNum);
    }
}
