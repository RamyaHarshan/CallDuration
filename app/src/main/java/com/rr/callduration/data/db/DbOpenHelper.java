package com.rr.callduration.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.rr.callduration.di.scope.ApplicationContext;
import com.rr.callduration.di.scope.DatabaseInfo;
import com.rr.callduration.main.model.CheckedTimeData;
import com.rr.callduration.main.model.DaoMaster;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DbOpenHelper extends DaoMaster.OpenHelper {

    @Inject
    public DbOpenHelper(@ApplicationContext Context context, @DatabaseInfo String dbName) {
        super(context,dbName);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch(oldVersion){
            case 1:
            case 2:
        }
    }

    public Long insertCheckedTime(CheckedTimeData checkedTimeData){
        //long id = ((DemoApplication)getApplication()).getDaoSession().getCheckedTimeDataDao().insert(checkedTimeData);

        return Long.valueOf(0);
    }
}
