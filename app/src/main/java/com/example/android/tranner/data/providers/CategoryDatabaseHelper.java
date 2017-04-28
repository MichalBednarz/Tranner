package com.example.android.tranner.data.providers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.tranner.data.providers.categoryprovider.Category;
import com.example.android.tranner.data.providers.itemprovider.CategoryItem;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Michał on 2017-04-27.
 */

public class CategoryDatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Category.db";

    private Dao<Category, Integer> mCategoryDao = null;
    private Dao<CategoryItem, Integer> mItemDao = null;

    public CategoryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Category.class);
            TableUtils.createTable(connectionSource, CategoryItem.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            //The last boolean, ignoreErrors, is to indicate if ORMLite should stop on first error or keep trying the rest.
            TableUtils.dropTable(connectionSource, Category.class, true);
            TableUtils.dropTable(connectionSource, CategoryItem.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        /* Category */

    public Dao<Category, Integer> getCategoryDao() throws SQLException {
        if (mCategoryDao == null) {
            mCategoryDao = getDao(Category.class);
        }

        return mCategoryDao;
    }

        /* Category item */

    public Dao<CategoryItem, Integer> getItemDao() throws SQLException {
        if (mItemDao == null) {
            mItemDao = getDao(CategoryItem.class);
        }

        return mItemDao;
    }

    @Override
    public void close() {
        mItemDao = null;
        super.close();
    }
}
