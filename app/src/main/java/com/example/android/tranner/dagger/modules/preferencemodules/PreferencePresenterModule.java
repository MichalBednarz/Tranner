package com.example.android.tranner.dagger.modules.preferencemodules;

import com.example.android.tranner.dagger.scopes.ActivityScope;
import com.example.android.tranner.data.providers.categorypreferences.PreferenceContract;
import com.example.android.tranner.data.providers.categorypreferences.PreferencePresenter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

/**
 * Created by Michał on 2017-05-06.
 */

@Module
public class PreferencePresenterModule {

    @Provides
    @ActivityScope
    public PreferencePresenter providesPreferencePresenter(PreferenceContract.Repository repository, Scheduler mainScheduler) {
        return new PreferencePresenter(repository, mainScheduler);
    }
}
