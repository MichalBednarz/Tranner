package com.example.android.tranner.dagger.components;

import com.example.android.tranner.dagger.modules.AppModule;
import com.example.android.tranner.dagger.modules.imagemodules.GsonModule;
import com.example.android.tranner.dagger.modules.imagemodules.HttpCacheModule;
import com.example.android.tranner.dagger.modules.imagemodules.SchedulerModule;
import com.example.android.tranner.dagger.modules.imagemodules.OkHttpClientModule;
import com.example.android.tranner.dagger.modules.imagemodules.PixabayServiceModule;
import com.example.android.tranner.dagger.modules.imagemodules.RetrofitModule;
import com.example.android.tranner.data.providers.imageprovider.PixabayService;

import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.Scheduler;

/**
 * Created by Michał on 2017-05-04.
 */

@Singleton
@Component(modules = {SchedulerModule.class, AppModule.class,
        GsonModule.class, HttpCacheModule.class, OkHttpClientModule.class, PixabayServiceModule.class, RetrofitModule.class})
public interface NetworkComponent {
    PixabayService providesPixabayService();

    Scheduler providesMainScheduler();
}
