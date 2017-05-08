package com.example.android.tranner.dagger.modules.detailmodules;

import com.example.android.tranner.dagger.scopes.ActivityScope;
import com.example.android.tranner.data.providers.CategoryDatabaseHelper;
import com.example.android.tranner.data.providers.detailprovider.DetailRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Michał on 2017-05-07.
 */

@Module
public class DetailRepositoryModule {
    @Provides
    @ActivityScope
    public DetailRepository providesDetailRepository(CategoryDatabaseHelper databaseHelper) {
        return new DetailRepository(databaseHelper);
    }
}
