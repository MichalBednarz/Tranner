package com.example.android.tranner.dagger.modules;

import com.example.android.tranner.dagger.scopes.ActivityScope;
import com.example.android.tranner.data.providers.CategoryDatabaseHelper;
import com.example.android.tranner.data.providers.categoryprovider.CategoryRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Michał on 2017-04-22.
 */

@Module
public class CategoryRepositoryModule {

    @Provides
    @ActivityScope
    public CategoryRepository providesRepository(CategoryDatabaseHelper helper) {
        return new CategoryRepository(helper);
    }

}
