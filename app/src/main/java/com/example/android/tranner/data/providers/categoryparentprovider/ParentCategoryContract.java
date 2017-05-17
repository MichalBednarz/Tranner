package com.example.android.tranner.data.providers.categoryparentprovider;

import com.example.android.tranner.data.providers.categoryprovider.Category;

/**
 * Created by Michał on 2017-05-06.
 */

public class ParentCategoryContract {
    public interface View {
        void onParentCategoryLoaded(Category parentCategory);

        void onParentCategoryLoadError();
    }

    public interface Presenter {
        void attachView(ParentCategoryContract.View view);

        void detachView();

        void loadParentCategory(int parentId);

    }
}
