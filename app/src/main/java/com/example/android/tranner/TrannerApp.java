package com.example.android.tranner;

import android.app.Application;

import com.example.android.tranner.mainscreen.dagger2.components.AppComponent;
import com.example.android.tranner.mainscreen.dagger2.components.DaggerAppComponent;
import com.example.android.tranner.mainscreen.dagger2.modules.AppModule;


/**
 * Created by Michał on 2017-04-22.
 */

public class TrannerApp extends Application {

    private AppComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getComponent() {
        return mComponent;
    }
}
