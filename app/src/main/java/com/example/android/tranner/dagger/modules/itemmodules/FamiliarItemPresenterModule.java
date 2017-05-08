package com.example.android.tranner.dagger.modules.itemmodules;

import com.example.android.tranner.dagger.scopes.FragmentScope;
import com.example.android.tranner.data.providers.itemprovider.FamiliarItemPresenter;
import com.example.android.tranner.data.providers.itemprovider.ItemRepository;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

/**
 * Created by Michał on 2017-05-03.
 */
@Module
public class FamiliarItemPresenterModule {

    @Provides
    @FragmentScope
    public FamiliarItemPresenter providesNewItemPresenter(ItemRepository repository, Scheduler mainScheduler) {
        return new FamiliarItemPresenter(repository, mainScheduler);
    }
}
