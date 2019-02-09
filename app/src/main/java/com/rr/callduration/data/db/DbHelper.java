package com.rr.callduration.data.db;

import com.rr.callduration.main.model.MainData;

public interface DbHelper {

    String getRecentAccessTime();

    void storeRecentAccessTime(long timeNow) throws Exception;
    MainData readCallLogTime(String fromDate, String toDate);
}
