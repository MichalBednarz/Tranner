package com.example.android.tranner.dagger.modules.categorymodules;

import com.example.android.tranner.dagger.scopes.ActivityScope;
import com.example.android.tranner.data.providers.categoryprovider.CategoryPresenter;
import com.example.android.tranner.data.providers.categoryprovider.CategoryRepository;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

/**
 * Created by Michał on 2017-04-11.
 */
@Module
public class CategoryPresenterModule {

    @Provides
    @ActivityScope
    public CategoryPresenter providesPresenter(CategoryRepository repository, Scheduler mainScheduler) {
        return new CategoryPresenter(repository, mainScheduler);
    }

}
