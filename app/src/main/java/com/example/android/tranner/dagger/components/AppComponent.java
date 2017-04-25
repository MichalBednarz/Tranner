package com.example.android.tranner.dagger.components;

import com.example.android.tranner.TrannerApp;
import com.example.android.tranner.dagger.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Michał on 2017-04-22.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    TrannerApp providesTrannerApp();
}
