package com.example.android.tranner.data.providers.categoryprovider;

import java.io.Serializable;

/**
 * Created by Michał on 2017-04-11.
 */

/**
 * Category POJO.
 */
public class Category implements Serializable {
    private String mCategory;
    private String mImageUrl;
    private long mId;

    public Category() {
    }

    public Category(String mCategory) {
        this.mCategory = mCategory;
    }

    /*
     * getters and setters
     *
     */

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        this.mCategory = category;
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

    public void setId(long id) {
        this.mId = id;
    }
}
