package com.rr.callduration.di.component;

import android.app.Application;
import android.content.Context;

import com.rr.callduration.DemoApplication;
import com.rr.callduration.data.DataManager;
import com.rr.callduration.di.module.ApplicationModule;
import com.rr.callduration.di.scope.ApplicationContext;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(DemoApplication demoApplication);

    @ApplicationContext
    Context getContext();

    Application getApplication();

    DataManager getDataManager();

}