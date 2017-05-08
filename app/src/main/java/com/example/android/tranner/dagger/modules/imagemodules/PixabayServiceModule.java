package com.example.android.tranner.dagger.modules.imagemodules;

import com.example.android.tranner.data.providers.imageprovider.PixabayService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Michał on 2017-05-08.
 */

@Module
public class PixabayServiceModule {
    @Provides
    @Singleton
    PixabayService providesPixabayService(Retrofit retrofit) {
        return new PixabayService(retrofit);
    }
}
