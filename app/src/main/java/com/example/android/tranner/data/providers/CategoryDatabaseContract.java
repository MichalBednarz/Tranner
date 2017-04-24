package com.example.android.tranner.data.providers;

import android.provider.BaseColumns;

import com.example.android.tranner.data.CategoryItem;

/**
 * Created by Michał on 2017-04-11.
 */

public final class CategoryDatabaseContract {


    private CategoryDatabaseContract() {
    }

    public static class CategoryEntry implements BaseColumns {
        public static final String TABLE_CATEGORY = "categories";
        public static final String TITLE_CATEGORY = "category";
        public static final String URL_CATEGORY = "url";
    }

    public static class ItemEntry implements BaseColumns {
        public static final String TABLE_ITEM = "items";
        public static final String TITLE_ITEM = "item";
        public static final String DESCRIPTION_ITEM = "description";
        public static final String PARENT_CATEGORY_ITEM = "parent_category";
    }

    public static class CategoryItemEntry implements BaseColumns {
        public static final String TABLE_CATEGORY_ITEM = "category_item";
        public static final String CATEGORY_ID = "table_id";
        public static final String ITEM_ID = "category_id";
    }

    public static final String CREATE_TABLE_CATEGORY =
            "CREATE TABLE " + CategoryEntry.TABLE_CATEGORY + " (" +
            CategoryEntry._ID + " INTEGER PRIMARY KEY," +
            CategoryEntry.TITLE_CATEGORY + " TEXT," +
            CategoryEntry.URL_CATEGORY + " TEXT)";

    public static final String CREATE_TABLE_ITEM =
            "CREATE TABLE " + ItemEntry.TABLE_ITEM + " (" +
            ItemEntry._ID + " INTEGER PRIMARY KEY," +
            ItemEntry.TITLE_ITEM + " TEXT," +
            ItemEntry.DESCRIPTION_ITEM + " TEX)" +
            ItemEntry.PARENT_CATEGORY_ITEM + " INTEGER NOT NULL," + " FOREIGN KEY (" +
            ItemEntry.PARENT_CATEGORY_ITEM + ") REFERENCES " + CategoryEntry.TABLE_CATEGORY +  "(" +
            ItemEntry.PARENT_CATEGORY_ITEM + ")";

    public static final String CREATE_TABLE_CATEGORY_ITEM =
            "CREATE TABLE " + CategoryItemEntry.TABLE_CATEGORY_ITEM + " (" +
                    CategoryItemEntry._ID + " INTEGER PRIMARY KEY," +
                    CategoryItemEntry.CATEGORY_ID + " INTEGER" +
                    CategoryItemEntry.ITEM_ID + " INTEGER)";


    public static final String SQL_DELETE_CATEGORY =
            "DROP TABLE IF EXISTS " + CategoryEntry.TABLE_CATEGORY;

    public static final String SQL_DELETE_ITEM =
            "DROP TABLE IF EXISTS " + ItemEntry.TABLE_ITEM;

    public static final String SQL_DELETE_CATEGORY_ITEM =
            "DROP TABLE IF EXISTS " + CategoryItemEntry.TABLE_CATEGORY_ITEM;
}
