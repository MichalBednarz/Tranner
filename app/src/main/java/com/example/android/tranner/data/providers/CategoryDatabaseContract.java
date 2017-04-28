package com.example.android.tranner.data.providers;

import android.provider.BaseColumns;

/**
 * Created by Michał on 2017-04-11.
 */

public final class CategoryDatabaseContract {



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
        public static final String ITEM_TAB = "item_tab";
    }

}
