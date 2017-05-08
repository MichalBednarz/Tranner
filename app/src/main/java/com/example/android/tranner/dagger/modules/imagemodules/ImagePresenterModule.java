package com.example.android.tranner.dagger.modules.imagemodules;

import com.example.android.tranner.dagger.scopes.FragmentScope;
import com.example.android.tranner.data.providers.imageprovider.ImagePresenter;
import com.example.android.tranner.data.providers.imageprovider.PixabayService;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

/**
 * Created by Michał on 2017-05-04.
 */

@Module
public class ImagePresenterModule {

    @Provides
    @FragmentScope
    ImagePresenter providesImagePresenter(PixabayService pixabayService, Scheduler mainScheduler) {
        return new ImagePresenter(pixabayService, mainScheduler);
    }

}
