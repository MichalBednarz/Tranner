package com.example.android.tranner.dagger.modules.imagemodules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Michał on 2017-05-04.
 */

@Module
public class SchedulerModule {

    @Provides
    @Singleton
    Scheduler providesMainScheduler() {
        return AndroidSchedulers.mainThread();

    }
}
