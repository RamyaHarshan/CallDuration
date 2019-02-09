package com.rr.callduration;

import com.rr.callduration.data.DataManager;
import com.rr.callduration.main.model.MainData;
import com.rr.callduration.main.presentation.MainMvpView;
import com.rr.callduration.main.presentation.MainPresenter;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.disposables.CompositeDisposable;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock
    MainMvpView mMockMainMvpView;
    @Mock
    DataManager mMockDataManager;

    private MainPresenter<MainMvpView> mMainPresenter;

    @BeforeClass
    public static void onlyOnce() throws Exception {

    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        mMainPresenter = new MainPresenter<>(mMockDataManager, compositeDisposable);
        mMainPresenter.onAttach(mMockMainMvpView);
    }

    @Test
    public void testReadCallLogTimeSuccess() {

        String startDate = "2019-01-16";
        String endDate = "2019-02-09";
        MainData mainData = new MainData();

        doReturn(mainData)
                .when(mMockDataManager)
                .readCallLogTime(startDate, endDate);
        mMainPresenter.readCallLogTime(startDate, endDate);

        verify(mMockMainMvpView).onCallDataReceived(mainData);
    }

    @After
    public void tearDown() throws Exception {
        mMainPresenter.onDetach();
    }

}
