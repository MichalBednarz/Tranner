package com.example.android.tranner;

import android.app.Application;

import com.example.android.tranner.dagger.components.AppComponent;
import com.example.android.tranner.dagger.components.DaggerAppComponent;
import com.example.android.tranner.dagger.components.DaggerNetworkComponent;
import com.example.android.tranner.dagger.components.NetworkComponent;
import com.example.android.tranner.dagger.modules.AppModule;
import com.example.android.tranner.dagger.modules.imagemodules.RetrofitModule;
import com.facebook.stetho.Stetho;

import static com.example.android.tranner.data.ConstantKeys.BASE_URL;


/**
 * Created by Michał on 2017-04-22.
 */

public class TrannerApp extends Application {

    private AppComponent mComponent;
    private NetworkComponent mNetworkComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        mComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        mNetworkComponent = DaggerNetworkComponent.builder()
                .appModule(new AppModule(this))
                .retrofitModule(new RetrofitModule(BASE_URL))
                .build();
    }

    public AppComponent getComponent() {
        return mComponent;
    }

    public NetworkComponent getNetworkingComponent() {
        return mNetworkComponent;
    }

}
