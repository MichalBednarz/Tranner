package com.example.android.tranner.mainscreen.dagger2;

import android.content.Context;

import com.example.android.tranner.mainscreen.MainActivity;
import com.example.android.tranner.mainscreen.mvp.CategoryPresenter;
import com.example.android.tranner.mainscreen.mvp.CategoryRepository;
import com.example.android.tranner.mainscreen.mvp.repository.CategoryDatabaseHelper;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Michał on 2017-04-11.
 */
@Module
public class MainActivityModule {

    private final MainActivity mActivity;

    public MainActivityModule(MainActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    public CategoryPresenter providesPresenter(MainActivity activity, CategoryRepository repository,Scheduler mainScheduler){
        return new CategoryPresenter(activity, repository, mainScheduler);
    }

    @Provides
    public CategoryRepository providesRepository(CategoryDatabaseHelper helper) {
        return new CategoryRepository(helper);
    }

    @Provides
    public CategoryDatabaseHelper providesHelper(MainActivity activity) {
        return new CategoryDatabaseHelper(activity);
    }

    @Provides
    public Scheduler providesMainScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    public MainActivity providesContext() {
        return mActivity;
    }

}
