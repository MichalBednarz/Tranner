package com.example.android.tranner.data.providers.itemprovider;

import com.example.android.tranner.data.Category;
import com.example.android.tranner.data.CategoryItem;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.android.tranner.data.providers.itemprovider.ItemContract.*;

/**
 * Created by Michał on 2017-04-23.
 */

public class ItemPresenter implements Actions {

    private Repository mRepository;
    private Scheduler mMainScheduler;
    private View mView;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public ItemPresenter(Repository repository, Scheduler mainScheduler) {
        this.mRepository = repository;
        this.mMainScheduler = mainScheduler;
    }

    public void setView(View view) {
        this.mView = view;
    }

    @Override
    public void loadItems(Category category) {
        mCompositeDisposable.add(mRepository.loadItems(category)
                .subscribeOn(Schedulers.io())
                .observeOn(mMainScheduler)
                .subscribeWith(new DisposableSingleObserver<List<CategoryItem>>() {
                    @Override
                    public void onSuccess(@NonNull List<CategoryItem> categoryItems) {
                        if (categoryItems.isEmpty()) {
                            mView.onNoItemLoaded();
                        } else {
                            mView.onItemLoaded(categoryItems);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.onItemLoadError();
                    }
                }));
    }

    @Override
    public void addItem(CategoryItem item) {
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
    public void deleteItem(CategoryItem item) {
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
    public void updateItem(CategoryItem item) {
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
        mCompositeDisposable.clear();
    }
}
