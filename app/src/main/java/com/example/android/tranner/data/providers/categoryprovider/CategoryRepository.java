package com.example.android.tranner.data.providers.categoryprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.tranner.data.Category;
import com.example.android.tranner.data.providers.CategoryDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

import static com.example.android.tranner.data.providers.CategoryDatabaseContract.CategoryEntry.TABLE_CATEGORY;
import static com.example.android.tranner.data.providers.CategoryDatabaseContract.CategoryEntry.TITLE_CATEGORY;
import static com.example.android.tranner.data.providers.CategoryDatabaseContract.CategoryEntry.URL_CATEGORY;
import static com.example.android.tranner.data.providers.CategoryDatabaseContract.CategoryEntry._ID;

/**
 * Created by Michał on 2017-04-11.
 */

public class CategoryRepository implements CategoryContract.Repository {

    private CategoryDatabaseHelper mDatabaseHelper;

    public CategoryRepository(CategoryDatabaseHelper mDatabaseHelper) {
        this.mDatabaseHelper = mDatabaseHelper;
    }

    @Override
    public Single<List<Category>> loadCategories() {
        List<Category> categoryList = new ArrayList<>();
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        String[] projection = {
                _ID,
                TITLE_CATEGORY,
                URL_CATEGORY
        };

        Cursor cursor = db.query(
                TABLE_CATEGORY,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        Category category;
        String name;
        String url;
        int indexCategory = cursor.getColumnIndex(TITLE_CATEGORY);
        int urlCategory = cursor.getColumnIndex(URL_CATEGORY);

        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            name = cursor.getString(indexCategory);
            url = cursor.getString(urlCategory);
            category = new Category(name);
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
        contentValues.put(TITLE_CATEGORY, category.getCategory());
        contentValues.put(URL_CATEGORY, (byte[]) null);
        long id = db.insert(TABLE_CATEGORY, null, contentValues);
        db.close();

        return Single.just(id);
    }

    @Override
    public Single<Integer> deleteCategory(Category category) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        String selection = TITLE_CATEGORY + " LIKE ?";
        String[] selectionArgs = {category.getCategory()};
        int rowNum = db.delete(TABLE_CATEGORY, selection, selectionArgs);
        db.close();

        return Single.just(rowNum);
    }

    @Override
    public Single<Integer> updateCategory(Category category) {
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(URL_CATEGORY, category.getImageUrl());
        String selection = TITLE_CATEGORY + " LIKE ?";
        String[] selectionArgs = {category.getCategory()};
        int rowNum = db.update(
                TABLE_CATEGORY,
                values,
                selection,
                selectionArgs);
        db.close();

        return Single.just(rowNum);
    }
}
