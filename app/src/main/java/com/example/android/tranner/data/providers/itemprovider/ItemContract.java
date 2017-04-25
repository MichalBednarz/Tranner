package com.example.android.tranner.data.providers.itemprovider;

import com.example.android.tranner.data.providers.categoryprovider.Category;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Michał on 2017-04-23.
 */

public class ItemContract {
    public interface View {
        void onItemLoaded(List<CategoryItem> categoryList);

        void onNoItemLoaded();

        void onItemLoadError();

        void onItemAdded();

        void onNoItemAdded();

        void onItemAddedError();

        void onItemDeleted();

        void onNoItemDeleted();

        void onItemDeletedError();

        void onItemUpdated();

        void onNoItemUpdated();

        void onItemUpdatedError();
    }

    public interface Actions {
        void loadItems(Category parentCategory);

        void addItem(CategoryItem item);

        void deleteItem(CategoryItem item);

        void updateItem(CategoryItem item);
    }

    public interface Repository {
        Single<List<CategoryItem>> loadItems(Category parentCategory);

        Single<Long> addItem(CategoryItem item);

        Single<Integer> deleteItem(CategoryItem item);

        Single<Integer> updateItem(CategoryItem item);
    }
}
