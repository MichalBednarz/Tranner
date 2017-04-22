package com.example.android.tranner.mainscreen.dagger2.components;

import com.example.android.tranner.TrannerApp;
import com.example.android.tranner.mainscreen.dagger2.modules.AppModule;

import dagger.Component;

/**
 * Created by Michał on 2017-04-22.
 */
@Component(modules = AppModule.class)
public interface AppComponent {
    TrannerApp providesTrannerApp();
}
