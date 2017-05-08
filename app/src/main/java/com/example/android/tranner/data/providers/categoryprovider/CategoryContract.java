package com.example.android.tranner.data.providers.categoryprovider;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Michał on 2017-04-11.
 */

public class CategoryContract {
    public interface View {
        void onCategoryLoaded(List<Category> categoryList);

        void onNoCategoryLoaded();

        void onCategoryLoadError();

        void onCategoryAdded();

        void onNoCategoryAdded();

        void onCategoryAddedError();

        void onCategoryDeleted();

        void onNoCategoryDeleted();

        void onCategoryDeletedError();

        void onCategoryUpdated();

        void onNoCategoryUpdated();

        void onCategoryUpdatedError();
    }

    public interface Presenter {
        void attachView(CategoryContract.View view);

        void detachView();

        void loadCategories();

        void addCategory(Category category);

        void deleteCategory(Category category);

        void updateCategory(Category category);
    }

    public interface Repository {
        Single<List<Category>> loadCategories();

        Single<Long> addCategory(Category category);

        Single<Integer> deleteCategory(Category category);

        Single<Integer> updateCategory(Category category);
    }
}
