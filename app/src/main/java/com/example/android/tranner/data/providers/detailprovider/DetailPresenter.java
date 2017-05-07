package com.example.android.tranner.data.providers.detailprovider;

import com.example.android.tranner.data.providers.itemprovider.CategoryItem;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Michał on 2017-05-07.
 */

public class DetailPresenter implements DetailContract.Presenter {

    private DetailContract.View mView;
    private DetailContract.Repository mRepository;
    private Scheduler mMainScheduler;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public DetailPresenter(DetailContract.Repository repository, Scheduler mainScheduler) {
        this.mRepository = repository;
        this.mMainScheduler = mainScheduler;
    }

    public void init(DetailContract.View view) {
        this.mView = view;
    }

    @Override
    public void loadItem(int itemId) {
        mCompositeDisposable.add(mRepository.loadItem(itemId)
                .subscribeOn(Schedulers.io())
                .observeOn(mMainScheduler)
                .subscribeWith(new DisposableSingleObserver<CategoryItem>() {
                    @Override
                    public void onSuccess(@NonNull CategoryItem item) {
                        mView.onItemLoaded(item);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.onItemLoadError();
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
                        mView.onItemUpdateError();
                    }
                }));
    }

    public void unsubscribe() {
        mCompositeDisposable.dispose();
    }
}
