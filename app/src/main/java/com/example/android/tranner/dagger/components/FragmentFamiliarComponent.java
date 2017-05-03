package com.example.android.tranner.dagger.components;

import com.example.android.tranner.categoryscreen.fragments.FragmentFamiliar;
import com.example.android.tranner.dagger.modules.FamiliarItemPresenterModule;
import com.example.android.tranner.dagger.scopes.FragmentScope;

import dagger.Component;

/**
 * Created by Michał on 2017-05-03.
 */

@FragmentScope
@Component(modules = {FamiliarItemPresenterModule.class},
        dependencies = CategoryActivityComponent.class)
public interface FragmentFamiliarComponent {
    void inject(FragmentFamiliar fragmentFamiliar);
}
