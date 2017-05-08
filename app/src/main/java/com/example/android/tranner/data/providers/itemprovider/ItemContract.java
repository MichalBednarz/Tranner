package com.example.android.tranner.data.providers.itemprovider;

import com.example.android.tranner.data.providers.categoryprovider.Category;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Michał on 2017-04-23.
 */

public class ItemContract {

    public interface NewView {
        void onNewItemLoaded(List<CategoryItem> newItemList);

        void onNoNewItemLoaded();

        void onNewItemLoadError();

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

    public interface FamiliarView {
        void onFamiliarItemLoaded(List<CategoryItem> familiarItemList);

        void onNoFamiliarItemLoaded();

        void onFamiliarItemLoadError();

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


    public interface NewPresenter {
        void attachNewView(ItemContract.NewView newView);

        void detachNewView();

        void loadNewItems(int parentCategory);

        void addNewItem(CategoryItem item);

        void deleteNewItem(CategoryItem item);

        void updateNewItem(CategoryItem item);
    }

    public interface FamiliarPresenter {
        void attachFamiliarView(ItemContract.FamiliarView newView);

        void detachFamiliarView();

        void loadFamiliarItems(int parentCategory);

        void addFamiliarItem(CategoryItem item);

        void deleteFamiliarItem(CategoryItem item);

        void updateFamiliarItem(CategoryItem item);

    }

    public interface Repository {
        Single<List<CategoryItem>> loadNewItems(int parentCategory);

        Single<List<CategoryItem>> loadFamiliarItems(int parentCategory);

        Single<Long> addItem(CategoryItem item);

        Single<Integer> deleteItem(CategoryItem item);

        Single<Integer> updateItem(CategoryItem item);
    }
}
