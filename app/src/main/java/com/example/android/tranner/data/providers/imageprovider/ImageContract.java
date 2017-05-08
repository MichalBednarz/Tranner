package com.example.android.tranner.data.providers.imageprovider;

import com.example.android.tranner.data.providers.itemprovider.ItemContract;

import io.reactivex.Single;

/**
 * Created by Michał on 2017-05-04.
 */

public class ImageContract {
    public interface View {
        void onWaitingForResults();

        void onStopWaiting();

        void onImagesFetched(PixabayResponse pixabayResponse);

        void onImageFetchError();
    }

    public interface Presenter {
        void attachView(ImageContract.View view);

        void detachView();

        void fetchImages(String query);
    }

    public interface Service {
        Single<PixabayResponse> fetchImages(String query);
    }

}
