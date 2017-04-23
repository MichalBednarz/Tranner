package com.example.android.tranner.mainscreen.mvp.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.android.tranner.mainscreen.mvp.repository.CategoryDatabaseContract.CREATE_TABLE_CATEGORY;
import static com.example.android.tranner.mainscreen.mvp.repository.CategoryDatabaseContract.CREATE_TABLE_CATEGORY_ITEM;
import static com.example.android.tranner.mainscreen.mvp.repository.CategoryDatabaseContract.CREATE_TABLE_ITEM;
import static com.example.android.tranner.mainscreen.mvp.repository.CategoryDatabaseContract.SQL_DELETE_CATEGORY;
import static com.example.android.tranner.mainscreen.mvp.repository.CategoryDatabaseContract.SQL_DELETE_CATEGORY_ITEM;
import static com.example.android.tranner.mainscreen.mvp.repository.CategoryDatabaseContract.SQL_DELETE_ITEM;

/**
 * Created by Michał on 2017-04-11.
 */

public class CategoryDatabaseHelper  extends SQLiteOpenHelper {

    private static final String TAG = "CategoryDatabaseHelper";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Category.db";

    public CategoryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_ITEM);
        db.execSQL(CREATE_TABLE_CATEGORY_ITEM);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_CATEGORY);
        db.execSQL(SQL_DELETE_ITEM);
        db.execSQL(SQL_DELETE_CATEGORY_ITEM);
        onCreate(db);
    }
}
