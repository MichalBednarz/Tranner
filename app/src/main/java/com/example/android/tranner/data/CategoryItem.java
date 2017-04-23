package com.example.android.tranner.data;

/**
 * Created by Michał on 2017-04-23.
 */

public class CategoryItem {

    private String mName;
    private String mDescription;

    public CategoryItem() {
    }

    public CategoryItem(String name) {
        mName = name;
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
