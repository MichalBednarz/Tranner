package com.example.android.tranner.data.providers.detailprovider;

import com.example.android.tranner.data.providers.CategoryDatabaseContract;
import com.example.android.tranner.data.providers.CategoryDatabaseHelper;
import com.example.android.tranner.data.providers.itemprovider.CategoryItem;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import io.reactivex.Single;

/**
 * Created by Michał on 2017-05-07.
 */

public class DetailRepository implements DetailContract.Repository {
    private CategoryDatabaseHelper mDatabaseHelper;
    private Dao<CategoryItem, Integer> mItemDao = null;

    public DetailRepository(CategoryDatabaseHelper databaseHelper) {
        this.mDatabaseHelper = databaseHelper;
    }

    @Override
    public Single<CategoryItem> loadItem(int itemId) {
        CategoryItem item = null;
        try {
            mItemDao = mDatabaseHelper.getItemDao();
            item = mItemDao.queryBuilder()
                    .where()
                    .eq(CategoryDatabaseContract.ItemEntry._ID, itemId)
                    .queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert item != null;
        return Single.just(item);
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
