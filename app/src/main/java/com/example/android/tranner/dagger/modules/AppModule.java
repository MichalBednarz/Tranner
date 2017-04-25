package com.example.android.tranner.dagger.modules;

import android.content.Context;

import com.example.android.tranner.TrannerApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Michał on 2017-04-22.
 */

@Module
public class AppModule {

    private TrannerApp mApp;

    public AppModule(TrannerApp trannerApp) {
        this.mApp = trannerApp;
    }

    @Provides
    @Singleton
    public TrannerApp providesApplication() {
        return mApp;
    }

    @Provides
    @Singleton
    public Context providesApplicationContext() {
        return mApp;
    }
}
