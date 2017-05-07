package com.example.android.tranner.data.providers.categorypreferences;

import android.util.Log;

import com.example.android.tranner.data.providers.categoryprovider.Category;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by Michał on 2017-05-06.
 */

public class PreferencePresenter implements PreferenceContract.Presenter {

    private PreferenceContract.Repository mRepository;
    private Scheduler mMainScheduler;
    private PreferenceContract.View mView;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public PreferencePresenter(PreferenceContract.Repository repository, Scheduler mainScheduler) {
        this.mRepository = repository;
        this.mMainScheduler = mainScheduler;
    }

    public void init(PreferenceContract.View view) {
        this.mView = view;
    }

    @Override
    public void saveParentId(int parentId) {
        mCompositeDisposable.add(mRepository.saveParentId(parentId)
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<Boolean>() {
                    @Override
                    public void onSuccess(@NonNull Boolean aBoolean) {
                        if (aBoolean) {
                            mView.onParentIdSaved();
                        } else {
                            mView.onParentIdSaveError();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.onParentIdSaveError();
                    }
                }));
    }

    @Override
    public void retrieveParentId() {
        mCompositeDisposable.add(mRepository.retrieveParentId()
                .subscribeOn(Schedulers.io())
                //.observeOn(mMainScheduler)
                .subscribeWith(new DisposableSingleObserver<Integer>() {
                    @Override
                    public void onSuccess(@NonNull Integer integer) {
                        if (integer == -1) {
                            mView.onNoParentIdRetrieved();
                        } else {
                            mView.onParentIdRetrieved(integer);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.onParentIdRetrieveError();
                    }
                }));

    }

    @Override
    public void loadParentCategory(int parentId) {
        mCompositeDisposable.add(mRepository.loadParentCategory(parentId)
                .subscribeOn(Schedulers.io())
                //.observeOn(mMainScheduler)
                .subscribeWith(new DisposableSingleObserver<Category>() {
                    @Override
                    public void onSuccess(@NonNull Category category) {
                        mView.onParentCategoryLoaded(category);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.onParentCategoryLoadError();
                    }
                }));
    }


    public void unsubscribe() {
        this.mCompositeDisposable.dispose();
    }
}
