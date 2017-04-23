package com.example.android.tranner.data;

/**
 * Created by Michał on 2017-04-23.
 */

public class CategoryItem {

    private String mName;
    private String mDescription;
    private int mId;

    public CategoryItem() {
    }

    public CategoryItem(String name) {
        mName = name;
    }

    /*
     * getters and setters
     */

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

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }


}
