package com.example.android.tranner.data.providers;

import android.content.Context;

import com.example.android.tranner.CustomTestRunner;
import com.example.android.tranner.data.providers.categoryprovider.Category;
import com.j256.ormlite.dao.Dao;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Michał on 2017-04-27.
 */

@RunWith(CustomTestRunner.class)
public class CategoryDatabaseHelperTest {

    private Context mContext;
    private CategoryDatabaseHelper categoryDatabaseHelper;
    private Dao<Category, Integer> mCategoryDao;

    @BeforeClass
    public static void setup() {
        // To redirect Robolectric to stdout
        System.setProperty("robolectric.logging", "stdout");
    }

    @Before
    public void initContext() throws SQLException {
        mContext = RuntimeEnvironment.application.getApplicationContext();
        categoryDatabaseHelper = new CategoryDatabaseHelper(mContext);
        mCategoryDao = categoryDatabaseHelper.getCategoryDao();
    }

    @Test
    public void isContextNotNull() {
        assertNotNull(mContext);
    }

    @Test
    public void isDatabaseHelperNotNull() {
        assertNotNull(categoryDatabaseHelper);
    }

    @Test
    public void isDaoNotNull() throws SQLException {
        assertNotNull(mCategoryDao);
    }




}