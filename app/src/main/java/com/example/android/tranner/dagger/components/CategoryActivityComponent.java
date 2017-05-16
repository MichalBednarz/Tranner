package com.example.android.tranner.dagger.components;

import com.example.android.tranner.categoryscreen.activities.CategoryActivity;
import com.example.android.tranner.dagger.modules.DatabaseHelperModule;
import com.example.android.tranner.dagger.modules.ThreadModule;
import com.example.android.tranner.dagger.modules.categorymodules.CategoryRepositoryModule;
import com.example.android.tranner.dagger.modules.itemmodules.ItemRepositoryModule;
import com.example.android.tranner.dagger.modules.parentmodules.ParentCategoryPresenterModule;
import com.example.android.tranner.dagger.scopes.ActivityScope;
import com.example.android.tranner.data.providers.itemprovider.ItemRepository;

import dagger.Component;
import io.reactivex.Scheduler;

/**
 * Created by Michał on 2017-04-24.
 */
@ActivityScope
@Component(modules = {ParentCategoryPresenterModule.class, CategoryRepositoryModule.class,
        ItemRepositoryModule.class, ThreadModule.class, DatabaseHelperModule.class},
        dependencies = AppComponent.class)
public interface CategoryActivityComponent {
    ItemRepository providesItemRepository();

    Scheduler providesMainScheduler();

    void inject(CategoryActivity categoryActivity);
}
