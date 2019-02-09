package com.rr.callduration.main.presentation;

import com.rr.callduration.base.MvpPresenter;
import com.rr.callduration.di.scope.PerActivity;

@PerActivity
public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {
    void setDateIntoFields();
    void readCallLogTime(String fromDate, String toDate);
    void storeRecentCheckedTime(long timeNow);
    void readRecentCheckedTime();
}
