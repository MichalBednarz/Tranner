package com.example.android.tranner.mainscreen.mvp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.tranner.data.Category;
import com.example.android.tranner.mainscreen.mvp.repository.CategoryDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

import static com.example.android.tranner.mainscreen.mvp.repository.CategoryDatabaseContract.CategoryEntry;

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
                CategoryEntry._ID,
                CategoryEntry.TITLE_CATEGORY,
                CategoryEntry.URL_CATEGORY
        };

        Cursor cursor = db.query(
                CategoryEntry.TABLE_CATEGORY,
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
        int indexCategory = cursor.getColumnIndex(CategoryEntry.TITLE_CATEGORY);
        int urlCategory = cursor.getColumnIndex(CategoryEntry.URL_CATEGORY);

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
        contentValues.put(CategoryEntry.TITLE_CATEGORY, category.getCategory());
        contentValues.put(CategoryEntry.URL_CATEGORY, (byte[]) null);
        long id = db.insert(CategoryEntry.TABLE_CATEGORY, null, contentValues);
        db.close();

        return Single.just(id);
    }

    @Override
    public Single<Integer> deleteCategory(Category category) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        String selection = CategoryEntry.TITLE_CATEGORY + " LIKE ?";
        String[] selectionArgs = {category.getCategory()};
        int rowNum = db.delete(CategoryEntry.TABLE_CATEGORY, selection, selectionArgs);
        db.close();

        return Single.just(rowNum);
    }

    @Override
    public Single<Integer> updateCategory(Category category) {
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(CategoryEntry.URL_CATEGORY, category.getImageUrl());
        String selection = CategoryEntry.TITLE_CATEGORY + " LIKE ?";
        String[] selectionArgs = {category.getCategory()};
        int rowNum = db.update(
                CategoryEntry.TABLE_CATEGORY,
                values,
                selection,
                selectionArgs);
        db.close();

        return Single.just(rowNum);
    }
}
