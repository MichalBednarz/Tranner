package com.example.android.tranner.dagger.components;

import com.example.android.tranner.categoryscreen.fragments.FragmentNew;
import com.example.android.tranner.dagger.modules.itemmodules.NewItemPresenterModule;
import com.example.android.tranner.dagger.scopes.FragmentScope;

import dagger.Component;

/**
 * Created by Michał on 2017-05-03.
 */

@FragmentScope
@Component(modules = {NewItemPresenterModule.class},
        dependencies = CategoryActivityComponent.class)
public interface FragmentNewComponent {
    void inject(FragmentNew fragmentNew);
}
