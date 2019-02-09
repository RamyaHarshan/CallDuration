package com.rr.callduration.main.presentation;

import com.rr.callduration.base.MvpView;
import com.rr.callduration.main.model.MainData;


public interface MainMvpView extends MvpView {

    void onCallDataReceived(MainData mainData);

    void onDateReceived(MainData mainData);

    void onLastCheckedReceived(String str);

}
