package com.example.android.tranner.mainscreen.dagger2.modules;

import com.example.android.tranner.TrannerApp;
import com.example.android.tranner.data.providers.categoryprovider.CategoryRepository;
import com.example.android.tranner.data.providers.CategoryDatabaseHelper;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Michał on 2017-04-22.
 */

@Module
public class RepositoryModule {

    @Provides
    public CategoryRepository providesRepository(CategoryDatabaseHelper helper) {
        return new CategoryRepository(helper);
    }

    @Provides
    public CategoryDatabaseHelper providesHelper(TrannerApp trannerApp) {
        return new CategoryDatabaseHelper(trannerApp);
    }
}
