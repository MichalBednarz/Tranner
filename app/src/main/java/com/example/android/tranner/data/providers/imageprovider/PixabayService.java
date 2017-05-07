package com.example.android.tranner.data.providers.imageprovider;

import io.reactivex.Single;
import retrofit2.Retrofit;

import static com.example.android.tranner.data.ConstantKeys.API_KEY;

/**
 * Created by Michał on 2017-05-04.
 */

public class PixabayService implements ImageContract.Service {
    private Retrofit mRetrofit;

    public PixabayService(Retrofit retrofit) {
        this.mRetrofit = retrofit;
    }

    @Override
    public Single<PixabayResponse> fetchImages(String query) {

        return mRetrofit.create(NetworkService.class).getImageList(API_KEY, query);
    }
}