package com.example.android.tranner.mainscreen.mvp;

import android.os.AsyncTask;

import com.example.android.tranner.mainscreen.MainActivity;
import com.example.android.tranner.mainscreen.data.Category;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.internal.operators.observable.ObservableBlockingSubscribe;

/**
 * Created by Michał on 2017-04-11.
 */

public class CategoryPresenter implements CategoryContract.Actions {
    private CategoryContract.View mView;
    private CategoryContract.Repository mRepository;

    public CategoryPresenter(CategoryContract.View view, CategoryContract.Repository repository) {
        this.mView = view;
        this.mRepository = repository;
    }

    @Override
    public void loadCategories() {
        new AsyncTask<Void, Void, List<Category>>() {
            @Override
            protected List<Category> doInBackground(Void... params) {
                return mRepository.loadCategories();
            }

            @Override
            protected void onPostExecute(List<Category> categoryList) {
                super.onPostExecute(categoryList);
                try {
                    if (categoryList.isEmpty()) {
                        mView.onNoCategoryLoaded();
                    } else {
                        mView.onCategoryLoaded(categoryList);
                    }
                } catch (NullPointerException e) {
                    mView.onCategoryLoadError();
                }

            }

        }.execute();
    }

    @Override
    public void addCategory(Category category) {

    }

    @Override
    public void deleteCategory(Category category) {

    }

    @Override
    public void updateCategory(Category category) {

    }


}
