package com.rr.callduration;

import android.app.Application;
import android.content.Context;

import com.rr.callduration.data.db.DbOpenHelper;
import com.rr.callduration.di.component.ApplicationComponent;
import com.rr.callduration.di.component.DaggerApplicationComponent;
import com.rr.callduration.di.module.ApplicationModule;
import com.rr.callduration.main.model.DaoMaster;
import com.rr.callduration.main.model.DaoSession;

public class DemoApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    private DaoSession daoSession;
    /*@Inject
    AppDbHelper dataManager;*/

    public static DemoApplication get(Context context) {
        return (DemoApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);


        daoSession = new DaoMaster(new DbOpenHelper(this, "daodemo.db")
                .getWritableDb())
                .newSession();

    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

}
