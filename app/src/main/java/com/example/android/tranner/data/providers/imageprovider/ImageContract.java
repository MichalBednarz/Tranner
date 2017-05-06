package com.example.android.tranner.data.providers.imageprovider;

import io.reactivex.Single;

/**
 * Created by Michał on 2017-05-04.
 */

public class ImageContract {
    public interface view {
        void onWaitingForResults();

        void onStopWaiting();

        void onImagesFetched(PixabayResponse pixabayResponse);

        void onImageFetchError();
    }

    public interface presenter {
        void fetchImages(String query);
    }

    public interface service {
        Single<PixabayResponse> fetchImages(String query);
    }

}
