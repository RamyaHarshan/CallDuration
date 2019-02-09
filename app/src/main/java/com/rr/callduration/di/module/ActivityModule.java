package com.rr.callduration.di.module;

import android.app.Activity;
import android.content.Context;

import com.rr.callduration.di.scope.ActivityContext;
import com.rr.callduration.di.scope.PerActivity;
import com.rr.callduration.main.presentation.MainMvpPresenter;
import com.rr.callduration.main.presentation.MainMvpView;
import com.rr.callduration.main.presentation.MainPresenter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> provideMainPresenter(MainPresenter<MainMvpView>
                                                               presenter) {
        return (MainMvpPresenter) presenter;
    }


}