package com.example.android.tranner.dagger.modules.preferencemodules;

import android.content.SharedPreferences;

import com.example.android.tranner.dagger.scopes.ActivityScope;
import com.example.android.tranner.data.providers.CategoryDatabaseHelper;
import com.example.android.tranner.data.providers.categorypreferences.PreferenceContract;
import com.example.android.tranner.data.providers.categorypreferences.PreferenceRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Michał on 2017-05-08.
 */
@Module
public class PreferenceRepositoryModule {
    @Provides
    @ActivityScope
    public PreferenceContract.Repository providesPreferenceRepository(CategoryDatabaseHelper helper, SharedPreferences sharedPreferences) {
        return new PreferenceRepository(helper, sharedPreferences);
    }
}
