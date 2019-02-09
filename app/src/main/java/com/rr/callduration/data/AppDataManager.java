package com.rr.callduration.data;

import android.content.Context;

import com.rr.callduration.data.db.DbHelper;
import com.rr.callduration.di.scope.ApplicationContext;
import com.rr.callduration.main.model.MainData;

import javax.inject.Inject;

public class AppDataManager implements DataManager{

    private static final String TAG = AppDataManager.class.getSimpleName();
    private final Context mContext;
    private final DbHelper mDbHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          DbHelper dbHelper) {
        mContext = context;
        mDbHelper = dbHelper;
    }


    @Override
    public String getRecentAccessTime() {
        return mDbHelper.getRecentAccessTime();
    }

    @Override
    public void storeRecentAccessTime(long timeNow) throws Exception {
        mDbHelper.storeRecentAccessTime(timeNow);
    }

    @Override
    public MainData readCallLogTime(String fromDate, String toDate) {
        return mDbHelper.readCallLogTime(fromDate, toDate);
    }
}
