package com.example.android.tranner.dagger.components;

import com.example.android.tranner.dagger.modules.DatabaseHelperModule;
import com.example.android.tranner.dagger.modules.DetailPresenterModule;
import com.example.android.tranner.dagger.modules.DetailRepositoryModule;
import com.example.android.tranner.dagger.modules.ThreadModule;
import com.example.android.tranner.dagger.scopes.ActivityScope;
import com.example.android.tranner.detailscreen.DetailActivity;

import dagger.Component;

/**
 * Created by Michał on 2017-05-07.
 */
@ActivityScope
@Component(modules = {DetailPresenterModule.class, DetailRepositoryModule.class, ThreadModule.class, DatabaseHelperModule.class},
        dependencies = AppComponent.class)
public interface DetailActivityComponent {
    void inject(DetailActivity detailActivity);
}
