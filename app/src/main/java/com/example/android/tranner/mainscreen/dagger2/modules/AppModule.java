package com.example.android.tranner.mainscreen.dagger2.modules;

import android.app.Application;

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
    public TrannerApp providesApplication() {
        return mApp;
    }
}
