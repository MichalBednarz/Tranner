package com.example.android.tranner.data.providers.itemprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.tranner.data.providers.CategoryDatabaseHelper;
import com.example.android.tranner.data.providers.categoryprovider.Category;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

import static com.example.android.tranner.data.providers.CategoryDatabaseContract.CategoryEntry;
import static com.example.android.tranner.data.providers.CategoryDatabaseContract.ItemEntry;


/**
 * Created by Michał on 2017-04-23.
 */

public class ItemRepository implements ItemContract.Repository {

    private static final String TAG = "ItemRepository";

    private CategoryDatabaseHelper mDatabaseHelper;

    public ItemRepository(CategoryDatabaseHelper databaseHelper) {
        this.mDatabaseHelper = databaseHelper;
    }

    @Override
    public Single<List<CategoryItem>> loadItems(Category parentCategory) {
        List<CategoryItem> itemList = new ArrayList<>();

        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();

        String[] projection = {
                ItemEntry._ID,
                ItemEntry.ITEM_TITLE,
                ItemEntry.ITEM_DESCRIPTION,
                CategoryEntry._ID
        };

        String selection = CategoryEntry._ID + " LIKE ?";

        String[] selectionArgs = {String.valueOf(parentCategory.getId())};

        Cursor cursor = db.query(
                ItemEntry.ITEM_TABLE,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int indexTitle = cursor.getColumnIndex(ItemEntry.ITEM_TITLE);
        int indexDescription = cursor.getColumnIndex(ItemEntry.ITEM_DESCRIPTION);

        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            String name = cursor.getString(indexTitle);
            String description = cursor.getString(indexDescription);
            CategoryItem item = new CategoryItem(name);
            if (description != null) {
                item.setDescription(description);
            }
            itemList.add(item);
        }
        cursor.close();
        db.close();

        return Single.just(itemList);
    }

    @Override
    public Single<Long> addItem(CategoryItem item) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ItemEntry.ITEM_TITLE, item.getName());
        contentValues.put(ItemEntry.ITEM_DESCRIPTION, item.getDescription());
        contentValues.put(CategoryEntry._ID, item.getParentCategoryId());
        long id = db.insert(ItemEntry.ITEM_TABLE, null, contentValues);

        db.close();

        return Single.just(id);
    }

    @Override
    public Single<Integer> deleteItem(CategoryItem item) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        String selection = ItemEntry.ITEM_TITLE + " LIKE ?";
        String[] selectionArgs = {item.getName()};
        int rowNum = db.delete(ItemEntry.ITEM_TABLE, selection, selectionArgs);

        db.close();

        return Single.just(rowNum);
    }

    @Override
    public Single<Integer> updateItem(CategoryItem item) {
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(ItemEntry.ITEM_DESCRIPTION, item.getDescription());
        String selection = ItemEntry.ITEM_TITLE + " LIKE ?";
        String[] selectionArgs = {item.getName()};
        int rowNum = db.update(
                ItemEntry.ITEM_TABLE,
                values,
                selection,
                selectionArgs);

        db.close();

        return Single.just(rowNum);
    }
}
