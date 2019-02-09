package com.rr.callduration.di.component;

import com.rr.callduration.di.module.ActivityModule;
import com.rr.callduration.di.scope.PerActivity;
import com.rr.callduration.main.presentation.MainActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

}