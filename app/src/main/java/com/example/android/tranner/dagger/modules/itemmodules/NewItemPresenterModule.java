package com.example.android.tranner.dagger.modules.itemmodules;

import com.example.android.tranner.dagger.scopes.FragmentScope;
import com.example.android.tranner.data.providers.itemprovider.ItemRepository;
import com.example.android.tranner.data.providers.itemprovider.NewItemPresenter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

/**
 * Created by Michał on 2017-05-03.
 */

@Module
public class NewItemPresenterModule {

    @Provides
    @FragmentScope
    public NewItemPresenter providesNewItemPresenter(ItemRepository repository, Scheduler mainScheduler) {
        return new NewItemPresenter(repository, mainScheduler);
    }
}
