package com.example.android.tranner.data.providers.categorypreferences;

import com.example.android.tranner.data.providers.categoryprovider.Category;

import io.reactivex.Single;

/**
 * Created by Michał on 2017-05-06.
 */

public class PreferenceContract {
    public interface View {
        void onParentCategoryLoaded(Category parentCategory);

        void onParentCategoryLoadError();

        void onParentIdSaved();

        void onParentIdSaveError();

        void onNoParentIdRetrieved();

        void onParentIdRetrieved(int parentId);

        void onParentIdRetrieveError();
    }

    public interface Presenter {
        void saveParentId(int parentId);

        void retrieveParentId();

        void loadParentCategory(int parentId);
    }

    public interface Repository {
        Single<Boolean> saveParentId(int parentId);

        Single<Integer> retrieveParentId();

        Single<Category> loadParentCategory(int parentId);
    }
}
