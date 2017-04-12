package com.example.android.tranner.mainscreen.data;

import java.io.Serializable;

/**
 * Created by Michał on 2017-04-11.
 */

public class Category implements Serializable {
    private String mCategory;
    private String mImageUrl;

    public Category(String mCategory) {
        this.mCategory = mCategory;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
