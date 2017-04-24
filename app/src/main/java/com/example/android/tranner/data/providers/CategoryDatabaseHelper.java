package com.example.android.tranner.data.providers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

/**
 * Created by Michał on 2017-04-11.
 */

public class CategoryDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "CategoryDatabaseHelper";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Category.db";

    public CategoryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CategoryDatabaseContract.CREATE_TABLE_CATEGORY);
        db.execSQL(CategoryDatabaseContract.CREATE_TABLE_ITEM);
        db.execSQL(CategoryDatabaseContract.CREATE_TABLE_CATEGORY_ITEM);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CategoryDatabaseContract.SQL_DELETE_CATEGORY);
        db.execSQL(CategoryDatabaseContract.SQL_DELETE_ITEM);
        db.execSQL(CategoryDatabaseContract.SQL_DELETE_CATEGORY_ITEM);
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys = ON;");
            }
        }
    }
}
