package com.example.android.tranner.mainscreen.mvp;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.android.tranner.mainscreen.data.Category;

import java.util.List;

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
    public void addCategory(final Category category) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                mRepository.addCategory(category);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mView.onCategoryAdded();
            }
        }.execute();
    }

    @Override
    public void deleteCategory(final Category category) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                mRepository.deleteCategory(category);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mView.onCategoryDeleted();
            }
        }.execute();
    }

    @Override
    public void updateCategory(final Category category) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                mRepository.updateCategory(category);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mView.onCategoryUpdated();
            }
        }.execute();
    }

    @Override
    public void closeDatabase() {
        mRepository.closeDatabase();
    }


}
