package com.example.android.tranner.dagger.components;

import com.example.android.tranner.dagger.modules.CategoryPresenterModule;
import com.example.android.tranner.dagger.modules.CategoryRepositoryModule;
import com.example.android.tranner.dagger.modules.DatabaseHelperModule;
import com.example.android.tranner.dagger.modules.ThreadModule;
import com.example.android.tranner.dagger.scopes.ActivityScope;
import com.example.android.tranner.mainscreen.MainActivity;

import dagger.Component;

/**
 * Created by Michał on 2017-04-11.
 */
@ActivityScope
@Component(modules = {CategoryPresenterModule.class, CategoryRepositoryModule.class, ThreadModule.class, DatabaseHelperModule.class},
        dependencies = AppComponent.class)
public interface CategoryPresenterComponent {
    void inject(MainActivity activity);
}
