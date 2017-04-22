package com.example.android.tranner.mainscreen.mvp;

import com.example.android.tranner.data.Category;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.android.tranner.mainscreen.mvp.CategoryContract.Actions;
import static com.example.android.tranner.mainscreen.mvp.CategoryContract.Repository;
import static com.example.android.tranner.mainscreen.mvp.CategoryContract.View;

/**
 * Created by Michał on 2017-04-11.
 */

public class CategoryPresenter implements Actions {
    private View mView;
    private Repository mRepository;
    private Scheduler mainScheduler;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public CategoryPresenter(View view, Repository repository, Scheduler mainScheduler) {
        this.mView = view;
        this.mRepository = repository;
        this.mainScheduler = mainScheduler;
    }

    @Override
    public void loadCategories() {
        mCompositeDisposable.add(mRepository.loadCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(mainScheduler)
                .subscribeWith(new DisposableSingleObserver<List<Category>>() {
                    @Override
                    public void onSuccess(@NonNull List<Category> audioDataList) {
                        if (audioDataList.isEmpty()) {
                            mView.onNoCategoryLoaded();
                        } else {
                            mView.onCategoryLoaded(audioDataList);
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
                .observeOn(mainScheduler)
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
                .observeOn(mainScheduler)
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
                .observeOn(mainScheduler)
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

    public void unsubscribe() {
        mCompositeDisposable.clear();
    }
}
