package com.example.android.tranner.mainscreen.dagger2.modules;

import android.content.Context;

import com.example.android.tranner.TrannerApp;
import com.example.android.tranner.mainscreen.MainActivity;
import com.example.android.tranner.mainscreen.adapters.MainActivityAdapter;
import com.example.android.tranner.mainscreen.mvp.CategoryContract;
import com.example.android.tranner.mainscreen.mvp.CategoryPresenter;
import com.example.android.tranner.mainscreen.mvp.CategoryRepository;
import com.example.android.tranner.mainscreen.mvp.repository.CategoryDatabaseHelper;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Michał on 2017-04-11.
 */
@Module
public class PresenterModule {

    @Provides
    public CategoryPresenter providesPresenter(CategoryRepository repository, Scheduler mainScheduler){
        return new CategoryPresenter(repository, mainScheduler);
    }

}
