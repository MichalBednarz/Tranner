package com.example.android.tranner.dagger.modules.imagemodules;

import com.example.android.tranner.TrannerApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;

/**
 * Created by Michał on 2017-05-08.
 */

@Module
public class HttpCacheModule {
    @Provides
    @Singleton
    Cache provideHttpCache(TrannerApp trannerApp) {
        int cacheSize = 10 * 1024 * 1024;
        return new Cache(trannerApp.getCacheDir(), cacheSize);
    }
}
