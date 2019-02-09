package com.rr.callduration.main.presentation;

import com.rr.callduration.base.BasePresenter;
import com.rr.callduration.data.DataManager;
import com.rr.callduration.main.model.MainData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


public class MainPresenter<V extends MainMvpView>
        extends BasePresenter<V>
        implements MainMvpPresenter<V> {

    @Inject
    public MainPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void setDateIntoFields(){
        MainData mainData = new MainData();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String todayDateStr = format.format(new Date());
        String fromDateStr = "";

        int previousDate = Calendar.getInstance().get(Calendar.DATE);
        int previousMonth = Calendar.getInstance().get(Calendar.MONTH);
        int previousMonthYear = Calendar.getInstance().get(Calendar.YEAR);
        if(Calendar.getInstance().get(Calendar.DATE)<16) {
            previousDate=16;
            previousMonth = ((previousMonth == 0) ? 11 : previousMonth - 1)+1;
            previousMonthYear = (previousMonth == 12) ? previousMonthYear - 1 : previousMonthYear;
        }else{
            previousDate=16;
            previousMonth = previousMonth + 1;

        }

        fromDateStr = previousMonthYear + "-" + String.format("%02d", previousMonth) + "-"+String.format("%02d", previousDate);
        mainData.setFromDate(fromDateStr);
        mainData.setToDate(todayDateStr);
        getMvpView().onDateReceived(mainData);
    }
    @Override
    public void readCallLogTime(String fromDate, String toDate) {
        MainData mainData = getDataManager().readCallLogTime(fromDate,toDate);
        getMvpView().onCallDataReceived(mainData);
    }

    public void storeRecentCheckedTime(long timeNow){
        try {
            getDataManager().storeRecentAccessTime(timeNow);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void readRecentCheckedTime(){

        getMvpView().onLastCheckedReceived(getDataManager().getRecentAccessTime());
    }
}
