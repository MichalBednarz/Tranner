package com.example.android.tranner.data.providers.categoryprovider;

import com.example.android.tranner.data.providers.CategoryDatabaseContract;
import com.example.android.tranner.data.providers.CategoryDatabaseHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

/**
 * Created by Michał on 2017-04-27.
 */

public class CategoryRepository implements CategoryContract.Repository{

    private CategoryDatabaseHelper mDatabaseHelper;
    private Dao<Category, Integer> mCategoryDao = null;

    public CategoryRepository(CategoryDatabaseHelper categoryDatabaseHelper) {
        this.mDatabaseHelper = categoryDatabaseHelper;
    }

    @Override
    public Single<List<Category>> loadCategories() {
        List<Category> categoryList = new ArrayList<>();
        try {
            mCategoryDao = mDatabaseHelper.getCategoryDao();
            categoryList = mCategoryDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Single.just(categoryList);
    }

    @Override
    public Single<Long> addCategory(Category category) {
        long rowNum = 0;
        try {
            mCategoryDao = mDatabaseHelper.getCategoryDao();
            rowNum = mCategoryDao.create(category);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Single.just(rowNum);
    }

    @Override
    public Single<Integer> deleteCategory(Category category) {
        int rowNum = 0;
        try {
            mCategoryDao = mDatabaseHelper.getCategoryDao();
            rowNum = mCategoryDao.delete(category);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Single.just(rowNum);
    }

    @Override
    public Single<Integer> updateCategory(Category category) {
        int rowNum = 0;
        try {
            mCategoryDao = mDatabaseHelper.getCategoryDao();
            rowNum = mCategoryDao.update(category);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Single.just(rowNum);
    }
}
