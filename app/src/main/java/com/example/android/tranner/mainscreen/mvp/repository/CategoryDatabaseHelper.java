package com.example.android.tranner.mainscreen.mvp.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.android.tranner.mainscreen.mvp.repository.CategoryEntryContract.SQL_CREATE_ENTRIES;
import static com.example.android.tranner.mainscreen.mvp.repository.CategoryEntryContract.SQL_DELETE_ENTRIES;

/**
 * Created by Michał on 2017-04-11.
 */

public class CategoryDatabaseHelper  extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Category.db";

    public CategoryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
