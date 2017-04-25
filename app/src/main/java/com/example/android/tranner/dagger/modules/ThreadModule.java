package com.example.android.tranner.dagger.modules;

import com.example.android.tranner.dagger.scopes.ActivityScope;

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
    @ActivityScope
    public Scheduler providesMainScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
