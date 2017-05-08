package com.example.android.tranner.dagger.components;

import com.example.android.tranner.dagger.modules.imagemodules.ImagePresenterModule;
import com.example.android.tranner.dagger.scopes.FragmentScope;
import com.example.android.tranner.mainscreen.dialogs.WebImageDialog;

import dagger.Component;

/**
 * Created by Michał on 2017-05-05.
 */

@FragmentScope
@Component(modules = ImagePresenterModule.class, dependencies = NetworkComponent.class)
public interface ImagePresenterComponent {
    void inject(WebImageDialog webImageDialog);
}
