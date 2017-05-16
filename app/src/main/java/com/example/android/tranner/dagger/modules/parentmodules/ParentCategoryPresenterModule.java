package com.example.android.tranner.dagger.modules.parentmodules;

import com.example.android.tranner.dagger.scopes.ActivityScope;
import com.example.android.tranner.data.providers.categoryparent.ParentCategoryPresenter;
import com.example.android.tranner.data.providers.categoryprovider.CategoryRepository;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

/**
 * Created by Michał on 2017-05-06.
 */

@Module
public class ParentCategoryPresenterModule {

    @Provides
    @ActivityScope
    public ParentCategoryPresenter providesPreferencePresenter(CategoryRepository repository, Scheduler mainScheduler) {
        return new ParentCategoryPresenter(repository, mainScheduler);
    }
}
