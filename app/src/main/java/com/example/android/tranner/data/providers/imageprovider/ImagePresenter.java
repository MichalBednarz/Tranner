package com.example.android.tranner.data.providers.imageprovider;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Michał on 2017-05-04.
 */

public class ImagePresenter implements ImageContract.Presenter {

    private ImageContract.View mView;
    private ImageContract.Service mService;
    private Scheduler mMainScheduler;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public ImagePresenter(ImageContract.Service service, Scheduler mainScheduler) {
        this.mService = service;
        this.mMainScheduler = mainScheduler;
    }

    @Override
    public void attachView(ImageContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        mCompositeDisposable.dispose();
    }

    @Override
    public void fetchImages(String query) {
        mView.onWaitingForResults();

        mCompositeDisposable.add(mService.fetchImages(query)
                .subscribeOn(Schedulers.io())
                .observeOn(mMainScheduler)
                .subscribeWith(new DisposableSingleObserver<PixabayResponse>() {
                    @Override
                    public void onSuccess(@NonNull PixabayResponse pixabayResponse) {
                        mView.onStopWaiting();
                        if(pixabayResponse.getTotal() > 0) {
                            mView.onImagesFetched(pixabayResponse);
                        } else {
                            mView.onNoImagesFetched();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.onStopWaiting();

                        mView.onImageFetchError();
                    }
                }));
    }
}
