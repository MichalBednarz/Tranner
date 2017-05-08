package com.example.android.tranner.dagger.components;

import com.example.android.tranner.categoryscreen.activities.CategoryActivity;
import com.example.android.tranner.dagger.modules.DatabaseHelperModule;
import com.example.android.tranner.dagger.modules.preferencemodules.PreferencePresenterModule;
import com.example.android.tranner.dagger.modules.preferencemodules.PreferenceRepositoryModule;
import com.example.android.tranner.dagger.modules.preferencemodules.SharedPreferencesModule;
import com.example.android.tranner.dagger.modules.ThreadModule;
import com.example.android.tranner.dagger.scopes.ActivityScope;

import dagger.Component;

/**
 * Created by Michał on 2017-05-08.
 */
@ActivityScope
@Component(modules = {PreferencePresenterModule.class, PreferenceRepositoryModule.class, SharedPreferencesModule.class,
        ThreadModule.class, DatabaseHelperModule.class},
        dependencies = AppComponent.class)
public interface PreferenceComponent {
    void inject(CategoryActivity categoryActivity);
}
