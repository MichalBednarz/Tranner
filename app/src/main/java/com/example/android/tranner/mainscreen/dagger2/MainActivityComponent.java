package com.example.android.tranner.mainscreen.dagger2;

import com.example.android.tranner.mainscreen.MainActivity;
import com.example.android.tranner.mainscreen.mvp.CategoryPresenter;
import com.example.android.tranner.mainscreen.mvp.CategoryRepository;

import dagger.Component;
import dagger.Provides;

/**
 * Created by Michał on 2017-04-11.
 */
@Component(modules = {MainActivityModule.class})
public interface MainActivityComponent {
    void inject(MainActivity activity);
}
