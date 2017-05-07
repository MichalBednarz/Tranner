package com.example.android.tranner.dagger.modules;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.android.tranner.TrannerApp;
import com.example.android.tranner.dagger.scopes.ActivityScope;
import com.example.android.tranner.data.providers.CategoryDatabaseHelper;
import com.example.android.tranner.data.providers.categorypreferences.PreferenceContract;
import com.example.android.tranner.data.providers.categorypreferences.PreferencePresenter;
import com.example.android.tranner.data.providers.categorypreferences.PreferenceRepository;

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

    @Provides
    @ActivityScope
    public PreferenceContract.Repository providesPreferenceRepository(CategoryDatabaseHelper helper, SharedPreferences sharedPreferences) {
        return new PreferenceRepository(helper, sharedPreferences);
    }

    @Provides
    @ActivityScope
    public SharedPreferences providesSharedPreferences(TrannerApp trannerApp) {
        return PreferenceManager.getDefaultSharedPreferences(trannerApp);
    }
}
