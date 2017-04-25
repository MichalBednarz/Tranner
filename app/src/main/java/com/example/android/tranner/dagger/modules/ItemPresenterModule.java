package com.example.android.tranner.dagger.modules;

import com.example.android.tranner.dagger.scopes.ActivityScope;
import com.example.android.tranner.data.providers.itemprovider.ItemPresenter;
import com.example.android.tranner.data.providers.itemprovider.ItemRepository;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

/**
 * Created by Michał on 2017-04-24.
 */
@Module
public class ItemPresenterModule {

    @Provides
    @ActivityScope
    public ItemPresenter providesItemPresenter(ItemRepository repository, Scheduler mainScheduler) {
        return new ItemPresenter(repository, mainScheduler);
    }
}
