package com.example.android.tranner.data.providers.imageprovider;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by Michał on 2017-05-04.
 */

public interface NetworkService {

    @GET("api/")
    Single<PixabayResponse> getImageList(@Query("key") String key, @Query("q") String query);
}
