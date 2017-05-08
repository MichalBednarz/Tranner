package com.example.android.tranner.dagger.modules.preferencemodules;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.android.tranner.TrannerApp;
import com.example.android.tranner.dagger.scopes.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Michał on 2017-05-08.
 */

@Module
public class SharedPreferencesModule {
    @Provides
    @ActivityScope
    public SharedPreferences providesSharedPreferences(TrannerApp trannerApp) {

        return PreferenceManager.getDefaultSharedPreferences(trannerApp);
    }
}
