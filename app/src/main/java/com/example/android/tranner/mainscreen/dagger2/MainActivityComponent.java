package com.example.android.tranner.mainscreen.dagger2;

import com.example.android.tranner.mainscreen.MainActivity;

import dagger.Component;

/**
 * Created by Michał on 2017-04-11.
 */
@Component(modules = {MainActivityModule.class})
public interface MainActivityComponent {
    void inject(MainActivity activity);
}
