package com.example.android.tranner.data.providers.categoryprovider;

import com.example.android.tranner.data.providers.CategoryDatabaseContract;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;


/**
 * Created by Michał on 2017-04-11.
 */

/**
 * Category POJO.
 */
@DatabaseTable(tableName = CategoryDatabaseContract.CategoryEntry.CATEGORY_TABLE)
public class Category implements Serializable{

    @DatabaseField(columnName = CategoryDatabaseContract.CategoryEntry._ID, generatedId = true)
    private int mId;

    @DatabaseField(columnName = CategoryDatabaseContract.CategoryEntry.CATEGORY_TITLE)
    private String mName;

    @DatabaseField(columnName = CategoryDatabaseContract.CategoryEntry.CATEGORY_URL)
    private String mImageUrl;

    public Category() {
        // Don't forget the empty constructor, needed by ORMLite.
    }

    public Category(String name) {
        this.mName = name;
    }

    /*
     * getters and setters
     *
     */

    public String getCategory() {
        return mName;
    }

    public void setCategory(String category) {
        this.mName = category;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.mImageUrl = imageUrl;
    }

    public long getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }
}