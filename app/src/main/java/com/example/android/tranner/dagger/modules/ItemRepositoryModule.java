package com.example.android.tranner.dagger.modules;

import com.example.android.tranner.dagger.scopes.ActivityScope;
import com.example.android.tranner.data.providers.CategoryDatabaseHelper;
import com.example.android.tranner.data.providers.itemprovider.ItemRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Michał on 2017-04-24.
 */
@Module
public class ItemRepositoryModule {

    @Provides
    @ActivityScope
    public ItemRepository providesItemRepository(CategoryDatabaseHelper helper) {
        return new ItemRepository(helper);
    }
}
