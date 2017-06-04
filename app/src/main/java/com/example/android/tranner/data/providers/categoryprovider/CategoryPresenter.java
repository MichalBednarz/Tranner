package com.example.android.tranner.data.providers.categoryprovider;

import android.support.v4.util.Pair;
import android.support.v7.util.DiffUtil;

import com.example.android.tranner.mainscreen.adapters.CategoryListDiffCallback;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.schedulers.Schedulers.computation;
import static java.util.Collections.*;

/**
 * Created by Michał on 2017-04-11.
 */

public class CategoryPresenter implements CategoryContract.Presenter {

    private CategoryContract.View mView;
    private CategoryContract.Repository mRepository;
    private Scheduler mMainScheduler;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public CategoryPresenter(CategoryContract.Repository repository, Scheduler mainScheduler) {
        this.mRepository = repository;
        this.mMainScheduler = mainScheduler;
    }

    @Override
    public void attachView(CategoryContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        mCompositeDisposable.dispose();
    }

    @Override
    public void loadCategories() {
        mCompositeDisposable.add(mRepository.loadCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(mMainScheduler)
                .subscribeWith(new DisposableSingleObserver<List<Category>>() {
                    @Override
                    public void onSuccess(@NonNull List<Category> categoryList) {
                        if (categoryList.isEmpty()) {
                            mView.onNoCategoryLoaded();
                        } else {
                            mView.onCategoryLoaded(categoryList);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.onCategoryLoadError();
                    }
                }));
    }

    @Override
    public void addCategory(final Category category) {
        mCompositeDisposable.add(mRepository.addCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(mMainScheduler)
                .subscribeWith(new DisposableSingleObserver<Long>() {
                    @Override
                    public void onSuccess(Long num) {
                        //database insertion returns -1 if failed
                        //else returns id number of inserted element
                        if (num == -1) {
                            mView.onNoCategoryAdded();
                        } else {
                            mView.onCategoryAdded();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onCategoryAddedError();
                    }
                }));
    }

    @Override
    public void deleteCategory(final Category category) {
        mCompositeDisposable.add(mRepository.deleteCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(mMainScheduler)
                .subscribeWith(new DisposableSingleObserver<Integer>() {
                    @Override
                    public void onSuccess(@NonNull Integer rowNum) {
                        //database delete method returns number of deleted rows
                        if (rowNum == 0) {
                            mView.onNoCategoryDeleted();
                        } else {
                            mView.onCategoryDeleted();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.onCategoryDeletedError();
                    }
                }));
    }

    @Override
    public void updateCategory(final Category category) {
        mCompositeDisposable.add(mRepository.updateCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(mMainScheduler)
                .subscribeWith(new DisposableSingleObserver<Integer>() {
                    @Override
                    public void onSuccess(@NonNull Integer rowNum) {
                        //database update returns number of rows affected
                        if (rowNum == 0) {
                            mView.onNoCategoryUpdated();
                        } else {
                            mView.onCategoryUpdated();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.onCategoryUpdatedError();
                    }
                }));
    }
}
