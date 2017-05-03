package com.example.android.tranner.data.providers.itemprovider;

import com.example.android.tranner.data.providers.categoryprovider.Category;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Michał on 2017-05-03.
 */

public class NewItemPresenter implements ItemContract.NewPresenter {
    private ItemContract.Repository mRepository;
    private Scheduler mMainScheduler;
    private ItemContract.NewView mView;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public NewItemPresenter(ItemContract.Repository repository, Scheduler mainScheduler) {
        this.mRepository = repository;
        this.mMainScheduler = mainScheduler;
    }

    public void setView(ItemContract.NewView view) {
        this.mView = view;
    }

    @Override
    public void loadNewItems(Category parentCategory) {
        mCompositeDisposable.add(mRepository.loadNewItems(parentCategory)
                .subscribeOn(Schedulers.io())
                .observeOn(mMainScheduler)
                .subscribeWith(new DisposableSingleObserver<List<CategoryItem>>() {
                    @Override
                    public void onSuccess(@NonNull List<CategoryItem> categoryItems) {
                        if (categoryItems.isEmpty()) {
                            mView.onNoNewItemLoaded();
                        } else {
                            mView.onNewItemLoaded(categoryItems);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.onNewItemLoadError();
                    }
                }));
    }


    @Override
    public void addNewItem(CategoryItem item) {
        mCompositeDisposable.add(mRepository.addItem(item)
                .subscribeOn(Schedulers.io())
                .observeOn(mMainScheduler)
                .subscribeWith(new DisposableSingleObserver<Long>() {
                    @Override
                    public void onSuccess(Long num) {
                        //database insertion returns -1 if failed
                        //else returns id number of inserted element
                        if (num == -1) {
                            mView.onNoItemAdded();
                        } else {
                            mView.onItemAdded();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onItemAddedError();
                    }
                }));

    }

    @Override
    public void deleteNewItem(CategoryItem item) {
        mCompositeDisposable.add(mRepository.deleteItem(item)
                .subscribeOn(Schedulers.io())
                .observeOn(mMainScheduler)
                .subscribeWith(new DisposableSingleObserver<Integer>() {
                    @Override
                    public void onSuccess(@NonNull Integer rowNum) {
                        //database delete method returns number of deleted rows
                        if (rowNum == 0) {
                            mView.onNoItemDeleted();
                        } else {
                            mView.onItemDeleted();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.onItemDeletedError();
                    }
                }));

    }

    @Override
    public void updateNewItem(CategoryItem item) {
        mCompositeDisposable.add(mRepository.updateItem(item)
                .subscribeOn(Schedulers.io())
                .observeOn(mMainScheduler)
                .subscribeWith(new DisposableSingleObserver<Integer>() {
                    @Override
                    public void onSuccess(@NonNull Integer rowNum) {
                        //database update returns number of rows affected
                        if (rowNum == 0) {
                            mView.onNoItemUpdated();
                        } else {
                            mView.onItemUpdated();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.onItemUpdatedError();
                    }
                }));

    }

    public void unsubscribe() {
        mCompositeDisposable.dispose();
    }
}
