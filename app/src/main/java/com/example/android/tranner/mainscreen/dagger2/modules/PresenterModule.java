package com.example.android.tranner.mainscreen.dagger2.modules;

import com.example.android.tranner.data.providers.categoryprovider.CategoryPresenter;
import com.example.android.tranner.data.providers.categoryprovider.CategoryRepository;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

/**
 * Created by Michał on 2017-04-11.
 */
@Module
public class PresenterModule {

    @Provides
    public CategoryPresenter providesPresenter(CategoryRepository repository, Scheduler mainScheduler){
        return new CategoryPresenter(repository, mainScheduler);
    }

}
