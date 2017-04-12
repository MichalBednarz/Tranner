package com.example.android.tranner.mainscreen.mvp.repository;

import android.provider.BaseColumns;

/**
 * Created by Michał on 2017-04-11.
 */

public final class CategoryEntryContract {


    private CategoryEntryContract() {
    }

    public static class CategoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "category";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_URL = "url";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + CategoryEntry.TABLE_NAME + " (" +
            CategoryEntry._ID + " INTEGER PRIMARY KEY," +
            CategoryEntry.COLUMN_NAME_TITLE + " TEXT," +
            CategoryEntry.COLUMN_NAME_URL + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CategoryEntry.TABLE_NAME;
}
