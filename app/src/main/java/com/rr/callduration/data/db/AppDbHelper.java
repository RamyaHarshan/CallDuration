package com.rr.callduration.data.db;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;

import com.rr.callduration.DemoApplication;
import com.rr.callduration.di.scope.ApplicationContext;
import com.rr.callduration.main.model.CheckedTimeData;
import com.rr.callduration.main.model.MainData;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppDbHelper implements DbHelper {
    private Context mContext;
    private DbOpenHelper dbOpenHelper;

    @Inject
    public AppDbHelper(@ApplicationContext Context mContext, DbOpenHelper dbOpenHelper) {
        this.mContext = mContext;
        this.dbOpenHelper = dbOpenHelper;
    }

    @Override
    public void storeRecentAccessTime(long timeNow) throws Exception {

        CheckedTimeData checkedTimeData = new CheckedTimeData();
        checkedTimeData.setLastCheckedDetails(timeNow);
        ((DemoApplication) mContext
                .getApplicationContext()).getDaoSession().getCheckedTimeDataDao().insert(checkedTimeData);
//        return dbOpenHelper.insertCheckedTime(checkedTimeData);
    }

    @Override
    public String getRecentAccessTime() {

        String str = "";
        List list = ((DemoApplication) mContext.getApplicationContext()).getDaoSession().getCheckedTimeDataDao().queryBuilder().list();//.limit(1).list();
        if (list != null) {
            if (list.size() > 0) {
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                CheckedTimeData checkedTimeData = (CheckedTimeData) list.get(list.size() - 1);
                Date date = new Date(checkedTimeData.getLastCheckedDetails());

                str = dateFormat.format(date);
            }
        }

        return str;
    }

    @Override
    public MainData readCallLogTime(String fromDate, String toDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String strOrder = android.provider.CallLog.Calls.DATE + " DESC";
        String selectionCondition = " " + CallLog.Calls.DATE + " >= ?" + " AND " + CallLog.Calls.DATE + " <= ?";
        MainData mainData = new MainData();
        try {
            String[] selectionArg1;

            selectionArg1 = new String[]{String.valueOf(format.parse(fromDate).getTime()), String.valueOf(format.parse(toDate).getTime())};

            Cursor managedCursor = mContext.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, selectionCondition, selectionArg1, strOrder);
            int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
            int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
            int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
            int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
            int totalMinutes = 700;
            int internationalMinutes = 0;
            int localMinutes = 0;

            int callDurInt = 0;
            while (managedCursor.moveToNext()) {
                String phNumber = managedCursor.getString(number);
                String callType = managedCursor.getString(type);
                String callDate = managedCursor.getString(date);
                Date callDayTime = new Date(Long.valueOf(callDate));
                String callDuration = managedCursor.getString(duration);
                String dir = null;
                int dircode = Integer.parseInt(callType);

                switch (dircode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        dir = "OUTGOING";
                        if (phNumber.length() == 8 || phNumber.startsWith("+65")) {
                            callDurInt = Integer.parseInt(callDuration);
                            if (callDurInt != 0)
                                localMinutes = localMinutes + (callDurInt / 60);
                            if (callDurInt % 60 != 0)
                                localMinutes = localMinutes + 1;
                        }

                        if (phNumber.startsWith("018") || phNumber.startsWith("+91")) {
                            callDurInt = Integer.parseInt(callDuration);
                            if (callDurInt != 0)
                                internationalMinutes = internationalMinutes + (callDurInt / 60);
                            if (callDurInt % 60 != 0)
                                internationalMinutes = internationalMinutes + 1;
                        }
                        callDurInt = 0;
                        break;

                    case CallLog.Calls.INCOMING_TYPE:
                        dir = "INCOMING";
                        break;

                    case CallLog.Calls.MISSED_TYPE:
                        dir = "MISSED";
                        break;
                }
            }
            managedCursor.close();


            mainData.setTotalCalls("" + totalMinutes);
            mainData.setLocalCalls(""+localMinutes);
            mainData.setInternationalCalls("" + (internationalMinutes));
            mainData.setRemainingCalls("" + (totalMinutes - internationalMinutes));
            /*fromDate.setText(fromDateStr);
            toDate.setText(todayDateStr);
            totalCalls.setText("" + totalMinutes);
            localCalls.setText("" + (localMinutes));
            internationalCalls.setText("" + (internationalMinutes));
            int remainingMin = totalMinutes - internationalMinutes;
            remainingCalls.setText("" + (totalMinutes - internationalMinutes));*/

           // getMvpView().onCallDataReceived(mainData);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mainData;
    }

}
