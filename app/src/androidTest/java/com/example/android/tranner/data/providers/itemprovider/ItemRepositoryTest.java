package com.example.android.tranner.data.providers.itemprovider;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.tranner.data.providers.categoryprovider.Category;
import com.example.android.tranner.data.providers.CategoryDatabaseHelper;
import com.example.android.tranner.data.providers.categoryprovider.CategoryRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Michał on 2017-04-25.
 */
@RunWith(AndroidJUnit4.class)
public class ItemRepositoryTest {

    private final Category CATEGORY = new Category();
    private final CategoryItem ITEM = new CategoryItem();

    private CategoryDatabaseHelper mDatabaseHelper;
    private ItemRepository mItemRepository;
    private CategoryRepository mCategoryRepository;

    @Before
    public void setUp() {
        mDatabaseHelper = new CategoryDatabaseHelper(InstrumentationRegistry.getTargetContext());
        mItemRepository = new ItemRepository(mDatabaseHelper);
        mCategoryRepository = new CategoryRepository(mDatabaseHelper);

    }

    @Test
    public void testPreconditions() {
        assertNotNull(mDatabaseHelper);
        assertNotNull(mItemRepository);
        assertNotNull(mCategoryRepository);
    }

    @Test
    public void shouldAddCategoryToDatabase() {
        long id = mCategoryRepository.addCategory(CATEGORY).blockingGet();

        assertFalse(id == -1);
    }

    /**
     * This test inserts to database item which has NO parent category id field.
     * Database's Item table requires parent category id not to be null.
     * This is why insertion should end in failure returning -1.
     * <p>
     * Item insertion needs parent Category to be inserted first.
     */
    @Test
    public void shouldAddNoItemToDatabase() {
        //given
        mCategoryRepository.addCategory(CATEGORY).blockingGet();

        //when
        long id = mItemRepository.addItem(ITEM).blockingGet();

        //then
        assertTrue(id == -1);
    }

    /**
     * This test inserts to database item which has parent category id field.
     * Database's Item table requires parent category id not to be null.
     * This is why insertion should end in success returning id of inserted row.
     * <p>
     * Item insertion needs parent Category to be inserted first.
     */
    @Test
    public void shouldAddItemToDatabase() {
        //given
        long categoryId = mCategoryRepository.addCategory(CATEGORY).blockingGet();
        CategoryItem item = ITEM;
        item.setParentCategoryId(categoryId);

        //when
        long itemId = mItemRepository.addItem(item).blockingGet();

        //then
        assertFalse(itemId == -1);
    }

    /**
     * This test inserts parent Category and attribute inserted Category instance
     * with its row id. Further item possessing parent Category instance id is inserted.
     * Finally test checks if one-item-list filtered by category instance id is returned .
     */
    @Test
    public void shouldRetrieveSingleData() {
        //given
        Category category = CATEGORY;
        long categoryId = mCategoryRepository.addCategory(category).blockingGet();
        category.setId(categoryId);

        //when
        CategoryItem item = ITEM;
        item.setParentCategoryId(categoryId);

        mItemRepository.addItem(item);

        //TODO selection doesn't work
        List<CategoryItem> itemList = mItemRepository.loadItems(category).blockingGet();

        //then
        assertFalse(itemList.isEmpty());
    }

}