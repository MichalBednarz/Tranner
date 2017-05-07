package com.example.android.tranner.dagger.components;

import com.example.android.tranner.categoryscreen.activities.CategoryActivity;
import com.example.android.tranner.dagger.modules.DatabaseHelperModule;
import com.example.android.tranner.dagger.modules.ItemRepositoryModule;
import com.example.android.tranner.dagger.modules.PreferencePresenterModule;
import com.example.android.tranner.dagger.modules.ThreadModule;
import com.example.android.tranner.dagger.scopes.ActivityScope;
import com.example.android.tranner.data.providers.itemprovider.ItemRepository;

import dagger.Component;
import io.reactivex.Scheduler;

/**
 * Created by Michał on 2017-04-24.
 */
@ActivityScope
@Component(modules = {PreferencePresenterModule.class, ItemRepositoryModule.class, ThreadModule.class, DatabaseHelperModule.class},
        dependencies = AppComponent.class)
public interface CategoryActivityComponent {
    ItemRepository providesItemRepository();

    Scheduler providesMainScheduler();

    void inject(CategoryActivity categoryActivity);
}
