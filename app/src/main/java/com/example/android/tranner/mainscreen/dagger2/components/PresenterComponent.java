package com.example.android.tranner.mainscreen.dagger2.components;

import com.example.android.tranner.mainscreen.MainActivity;
import com.example.android.tranner.mainscreen.dagger2.modules.PresenterModule;
import com.example.android.tranner.mainscreen.dagger2.modules.RepositoryModule;
import com.example.android.tranner.mainscreen.dagger2.modules.ThreadModule;

import dagger.Component;

/**
 * Created by Michał on 2017-04-11.
 */
@Component(modules = {PresenterModule.class, RepositoryModule.class, ThreadModule.class}, dependencies = AppComponent.class)
public interface PresenterComponent {
    void inject(MainActivity activity);
}
