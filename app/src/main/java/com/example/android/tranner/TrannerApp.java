package com.example.android.tranner;

import android.app.Application;

import com.example.android.tranner.dagger.components.AppComponent;
import com.example.android.tranner.dagger.components.DaggerAppComponent;
import com.example.android.tranner.dagger.modules.AppModule;
import com.facebook.stetho.Stetho;


/**
 * Created by Michał on 2017-04-22.
 */

public class TrannerApp extends Application {

    private AppComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        mComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getComponent() {
        return mComponent;
    }
}
