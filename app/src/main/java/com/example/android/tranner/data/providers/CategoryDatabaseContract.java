package com.example.android.tranner.data.providers;

import android.provider.BaseColumns;

/**
 * Created by Michał on 2017-04-11.
 */

public final class CategoryDatabaseContract {

    public static final String CREATE_TABLE_CATEGORY =
            "CREATE TABLE " + CategoryEntry.CATEGORY_TABLE + " (" +
                    CategoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    CategoryEntry.CATEGORY_TITLE + " TEXT," +
                    CategoryEntry.CATEGORY_URL + " TEXT)";

    public static final String CREATE_TABLE_ITEM =
            "CREATE TABLE " + ItemEntry.ITEM_TABLE + " (" +
                    ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ItemEntry.ITEM_TITLE + " TEXT, " +
                    ItemEntry.ITEM_DESCRIPTION + " TEXT " +
                    ItemEntry.ITEM_PARENT_CATEGORY + " INTEGER NOT NULL REFERENCES " + CategoryEntry.CATEGORY_TABLE + " (" +
                    CategoryEntry._ID + "))";

    public static final String SQL_DELETE_CATEGORY =
            "DROP TABLE IF EXISTS " + CategoryEntry.CATEGORY_TABLE;

    public static final String SQL_DELETE_ITEM =
            "DROP TABLE IF EXISTS " + ItemEntry.ITEM_TABLE;

    private CategoryDatabaseContract() {
    }

    public static class CategoryEntry implements BaseColumns {
        public static final String CATEGORY_TABLE = "category_table";
        public static final String CATEGORY_TITLE = "category_title";
        public static final String CATEGORY_URL = "category_url";
    }

    public static class ItemEntry implements BaseColumns {
        public static final String ITEM_TABLE = "item_table";
        public static final String ITEM_TITLE = "item_title";
        public static final String ITEM_DESCRIPTION = "item_description";
        public static final String ITEM_PARENT_CATEGORY = "item_parent_category";
    }

}
