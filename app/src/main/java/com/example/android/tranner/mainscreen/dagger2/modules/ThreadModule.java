package com.example.android.tranner.mainscreen.dagger2.modules;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Michał on 2017-04-22.
 */
@Module
public class ThreadModule {

    @Provides
    public Scheduler providesMainScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
