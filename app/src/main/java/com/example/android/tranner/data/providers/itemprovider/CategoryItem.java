package com.example.android.tranner.data.providers.itemprovider;

/**
 * Created by Michał on 2017-04-23.
 */

/**
 * CategoryItem POJO.
 */
public class CategoryItem {

    private String mName;
    private String mDescription;
    private String mTab;
    private int mItemId;
    private long mParentId;

    public CategoryItem() {
    }

    public CategoryItem(String name) {
        this.mName = name;
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
        return mItemId;
    }

    public void setId(int id) {
        this.mItemId = id;
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


}
