package com.example.android.tranner.dagger.components;

import com.example.android.tranner.dagger.modules.AppModule;
import com.example.android.tranner.dagger.modules.ImagePresenterModule;
import com.example.android.tranner.dagger.modules.NetworkModule;
import com.example.android.tranner.data.providers.imageprovider.PixabayService;

import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.Scheduler;

/**
 * Created by Michał on 2017-05-04.
 */

@Singleton
@Component(modules = {NetworkModule.class, AppModule.class, ImagePresenterModule.class})
public interface NetworkComponent {
    PixabayService providesPixabayService();

    Scheduler providesMainScheduler();
}
