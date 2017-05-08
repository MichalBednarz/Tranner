package com.example.android.tranner.dagger.modules.detailmodules;

import com.example.android.tranner.dagger.scopes.ActivityScope;
import com.example.android.tranner.data.providers.detailprovider.DetailPresenter;
import com.example.android.tranner.data.providers.detailprovider.DetailRepository;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

/**
 * Created by Michał on 2017-05-07.
 */
@Module
public class DetailPresenterModule {
    @Provides
    @ActivityScope
    public DetailPresenter providesDetailPresenter(DetailRepository repository, Scheduler mainScheduler) {
        return new DetailPresenter(repository, mainScheduler);
    }
}
