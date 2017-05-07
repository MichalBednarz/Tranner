package com.example.android.tranner.data.providers.detailprovider;

import com.example.android.tranner.data.providers.itemprovider.CategoryItem;

import io.reactivex.Single;

/**
 * Created by Michał on 2017-05-07.
 */

public class DetailContract {
    public interface View {
        void onItemLoaded(CategoryItem item);

        void onItemLoadError();

        void onItemUpdated();

        void onNoItemUpdated();

        void onItemUpdateError();
    }

    public interface Presenter {
        void loadItem(int itemId);

        void updateItem(CategoryItem item);
    }

    public interface Repository {
        Single<CategoryItem> loadItem(int itemId);

        Single<Integer> updateItem(CategoryItem item);
    }
}
