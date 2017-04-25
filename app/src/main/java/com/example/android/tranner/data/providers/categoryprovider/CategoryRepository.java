package com.example.android.tranner.data.providers.categoryprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.tranner.data.providers.CategoryDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

import static com.example.android.tranner.data.providers.CategoryDatabaseContract.CategoryEntry.CATEGORY_TABLE;
import static com.example.android.tranner.data.providers.CategoryDatabaseContract.CategoryEntry.CATEGORY_TITLE;
import static com.example.android.tranner.data.providers.CategoryDatabaseContract.CategoryEntry.CATEGORY_URL;
import static com.example.android.tranner.data.providers.CategoryDatabaseContract.CategoryEntry._ID;

/**
 * Created by Michał on 2017-04-11.
 */

public class CategoryRepository implements CategoryContract.Repository {

    private CategoryDatabaseHelper mDatabaseHelper;

    public CategoryRepository(CategoryDatabaseHelper databaseHelper) {
        this.mDatabaseHelper = databaseHelper;
    }

    @Override
    public Single<List<Category>> loadCategories() {
        List<Category> categoryList = new ArrayList<>();

        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();

        String[] projection = {
                _ID,
                CATEGORY_TITLE,
                CATEGORY_URL
        };

        Cursor cursor = db.query(
                CATEGORY_TABLE,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        int indexCategory = cursor.getColumnIndex(CATEGORY_TITLE);
        int urlCategory = cursor.getColumnIndex(CATEGORY_URL);

        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            String name = cursor.getString(indexCategory);
            String url = cursor.getString(urlCategory);
            Category category = new Category(name);
            if (url != null) {
                category.setImageUrl(url);
            }
            categoryList.add(category);
        }
        cursor.close();
        db.close();

        return Single.just(categoryList);
    }

    @Override
    public Single<Long> addCategory(Category category) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY_TITLE, category.getCategory());
        contentValues.put(CATEGORY_URL, (byte[]) null);
        long id = db.insert(CATEGORY_TABLE, null, contentValues);

        db.close();

        return Single.just(id);
    }

    @Override
    public Single<Integer> deleteCategory(Category category) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

        String selection = CATEGORY_TITLE + " LIKE ?";
        String[] selectionArgs = {category.getCategory()};
        int rowNum = db.delete(CATEGORY_TABLE, selection, selectionArgs);

        db.close();

        return Single.just(rowNum);
    }

    @Override
    public Single<Integer> updateCategory(Category category) {
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(CATEGORY_URL, category.getImageUrl());
        String selection = CATEGORY_TITLE + " LIKE ?";
        String[] selectionArgs = {category.getCategory()};
        int rowNum = db.update(
                CATEGORY_TABLE,
                values,
                selection,
                selectionArgs);

        db.close();

        return Single.just(rowNum);
    }
}
