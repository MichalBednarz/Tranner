package com.example.android.tranner.data;

/**
 * Created by Michał on 2017-04-23.
 */

public class CategoryItem {

    private String mName;
    private String mDescription;
    private String mTab;
    private int mItemId;
    private int mParentCategoryId;

    public CategoryItem() {
    }

    public CategoryItem(String name, int parentCategoryId) {
        this.mName = name;
        this.mParentCategoryId = parentCategoryId;
    }

    /*
     * getters and setters
     */

    public void setParentCategoryId(int parentCategoryId) {
        this.mParentCategoryId = parentCategoryId;
    }

    public int getParentCategoryId() {
        return mParentCategoryId;
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

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }


}
