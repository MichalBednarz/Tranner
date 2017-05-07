package com.example.android.tranner.data.providers.categorypreferences;

import android.content.SharedPreferences;

import com.example.android.tranner.data.providers.CategoryDatabaseContract;
import com.example.android.tranner.data.providers.CategoryDatabaseHelper;
import com.example.android.tranner.data.providers.categoryprovider.Category;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import io.reactivex.Single;

/**
 * Created by Michał on 2017-05-06.
 */

public class PreferenceRepository implements PreferenceContract.Repository {

    private static final String PARENT_KEY = "parent_id";
    private static final int DEFAULT_ID = -1;

    private SharedPreferences mPreferences;
    private RxSharedPreferences mRxPreferences;
    private CategoryDatabaseHelper mDatabaseHelper;
    private Dao<Category, Integer> mCategoryDao = null;

    public PreferenceRepository(CategoryDatabaseHelper databaseHelper, SharedPreferences sharedPreferences) {
        this.mDatabaseHelper = databaseHelper;
        this.mPreferences = sharedPreferences;
        this.mRxPreferences = RxSharedPreferences.create(sharedPreferences);
    }

    @Override
    public Single<Integer> retrieveParentId() {
        return mRxPreferences.getInteger(PARENT_KEY, DEFAULT_ID)
                .asObservable()
                .firstOrError();
    }

    @Override
    public Single<Category> loadParentCategory(int parentId) {
        Category category = null;
        try {
            mCategoryDao = mDatabaseHelper.getCategoryDao();
            category = mCategoryDao.queryBuilder()
                    .where()
                    .eq(CategoryDatabaseContract.CategoryEntry._ID, parentId)
                    .queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert category != null;
        return Single.just(category);
    }

    @Override
    public Single<Boolean> saveParentId(int parentId) {
        SharedPreferences.Editor prefsEditor = mPreferences.edit();
        prefsEditor.putInt(PARENT_KEY, parentId);

        return Single.just(prefsEditor.commit());
    }
}
