package com.example.android.tranner.dagger.components;

import com.example.android.tranner.categoryscreen.activities.CategoryActivity;
import com.example.android.tranner.dagger.modules.DatabaseHelperModule;
import com.example.android.tranner.dagger.modules.ItemPresenterModule;
import com.example.android.tranner.dagger.modules.ItemRepositoryModule;
import com.example.android.tranner.dagger.modules.ThreadModule;
import com.example.android.tranner.dagger.scopes.ActivityScope;

import dagger.Component;

/**
 * Created by Michał on 2017-04-24.
 */
@ActivityScope
@Component(modules = {ItemPresenterModule.class, ItemRepositoryModule.class, ThreadModule.class, DatabaseHelperModule.class},
        dependencies = AppComponent.class)
public interface ItemPresenterComponent {
    void inject(CategoryActivity activity);
}
