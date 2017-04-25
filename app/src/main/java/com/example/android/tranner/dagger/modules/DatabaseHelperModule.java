package com.example.android.tranner.dagger.modules;

import com.example.android.tranner.TrannerApp;
import com.example.android.tranner.dagger.scopes.ActivityScope;
import com.example.android.tranner.data.providers.CategoryDatabaseHelper;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Michał on 2017-04-24.
 */
@Module
public class DatabaseHelperModule {

    @Provides
    @ActivityScope
    public CategoryDatabaseHelper providesHelper(TrannerApp trannerApp) {
        return new CategoryDatabaseHelper(trannerApp);
    }
}
