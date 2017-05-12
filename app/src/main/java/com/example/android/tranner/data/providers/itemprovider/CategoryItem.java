package com.example.android.tranner.data.providers.itemprovider;

/**
 * Created by Michał on 2017-04-23.
 */

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

import static com.example.android.tranner.data.providers.CategoryDatabaseContract.ItemEntry;

/**
 * CategoryItem POJO.
 */
@DatabaseTable(tableName = ItemEntry.ITEM_TABLE)
public class CategoryItem implements Serializable {

    @DatabaseField(columnName = ItemEntry._ID, generatedId = true)
    private int mId;

    @DatabaseField(columnName = ItemEntry.ITEM_TITLE)
    private String mName;

    @DatabaseField(columnName = ItemEntry.ITEM_DESCRIPTION)
    private String mDescription;

    @DatabaseField(columnName = ItemEntry.ITEM_PARENT_CATEGORY)
    private long mParentId;

    @DatabaseField(columnName = ItemEntry.ITEM_TAB)
    private String mTab;

    @DatabaseField(columnName = ItemEntry.ITEM_IMAGE_URI)
    private String mImageUri;

    public CategoryItem() {
    }

    public CategoryItem(String name, long parentCategoryId, String tab) {
        this.mName = name;
        this.mParentId = parentCategoryId;
        this.mTab = tab;
    }

    /*
     * getters and setters
     *
     */

    public long getParentCategoryId() {
        return mParentId;
    }

    public void setParentCategoryId(long parentId) {
        this.mParentId = parentId;
    }

    public String getTab() {
        return mTab;
    }

    public void setTab(String tab) {
        this.mTab = tab;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getImageUri() {
        return mImageUri;
    }

    public void setImageUri(String imageUri) {
        this.mImageUri = imageUri;
    }
}
