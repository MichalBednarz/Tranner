package com.example.android.tranner.data.providers.categoryparentprovider;

import com.example.android.tranner.data.providers.categoryprovider.Category;
import com.example.android.tranner.data.providers.categoryprovider.CategoryContract;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Michał on 2017-05-06.
 */

public class ParentCategoryPresenter implements ParentCategoryContract.Presenter {

    private CategoryContract.Repository mRepository;
    private Scheduler mMainScheduler;
    private ParentCategoryContract.View mView;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public ParentCategoryPresenter(CategoryContract.Repository repository, Scheduler mainScheduler) {
        this.mRepository = repository;
        this.mMainScheduler = mainScheduler;
    }

    @Override
    public void attachView(ParentCategoryContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        mCompositeDisposable.dispose();
    }

    @Override
    public void loadParentCategory(int parentId) {
        mCompositeDisposable.add(mRepository.loadParentCategory(parentId)
                .subscribeOn(Schedulers.io())
                .observeOn(mMainScheduler)
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
}
