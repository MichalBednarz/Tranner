package com.example.android.tranner.data.providers.categoryprovider;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Michał on 2017-04-24.
 */
@RunWith(AndroidJUnit4.class)
public class OrmCategoryRepositoryTest {

    private CategoryDatabaseHelper mDatabaseHelper;
    private CategoryRepository mRepository;

    @Before
    public void setUp() {
        mDatabaseHelper = new CategoryDatabaseHelper(InstrumentationRegistry.getTargetContext());
        mRepository = new CategoryRepository(mDatabaseHelper);
    }

    @Test
    public void testPreconditions() {
        assertNotNull(mDatabaseHelper);
        assertNotNull(mRepository);
    }

    @Test
    public void testShouldAddCategory() throws Exception {
        mRepository.addCategory(new Category("category"));

        List<Category> categoryList = mRepository.loadCategories().blockingGet();

        assertFalse(categoryList.isEmpty());

        assertTrue(categoryList.get(0).getCategory().equals("category"));

    }
}