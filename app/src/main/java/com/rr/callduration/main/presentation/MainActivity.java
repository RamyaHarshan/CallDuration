package com.rr.callduration.main.presentation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rr.callduration.R;
import com.rr.callduration.base.BaseActivity;
import com.rr.callduration.main.model.MainData;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.Manifest.permission.READ_CALL_LOG;


public class MainActivity extends BaseActivity implements MainMvpView {

    private static final int PERMISSION_REQUEST_CODE = 200;

    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;

    @BindView(R.id.fromDate)
    TextView fromDate;
    @BindView(R.id.toDate)
    TextView toDate;
    @BindView(R.id.totalCall)
    TextView totalCalls;
    @BindView(R.id.localCalls)
    TextView localCalls;
    @BindView(R.id.internationalCalls)
    TextView internationalCalls;
    @BindView(R.id.remainingCalls)
    TextView remainingCalls;
    @BindView(R.id.lastchecked)
    TextView lastchecked;
    @BindView(R.id.head)
    RelativeLayout head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActivityComponent().inject(this);

        /*
        Below call initializes Butterknife library to find and process annotated fields and
        methods in the current activity. Current activity layout is used as the view root.
         */
        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        mPresenter.setDateIntoFields();
        mPresenter.readRecentCheckedTime();

        if (checkPermission()) {
            mPresenter.readCallLogTime(fromDate.getText().toString(),toDate.getText().toString());
        } else {
            requestPermission();
        }
        mPresenter.storeRecentCheckedTime((new Date()).getTime());

    }

    @Override
    protected void setUp() {

    }

    @Override
    protected void onStart() {
        super.onStart();
//        presenter.calculateCallTimimg(fromDate.getText().toString(),toDate.getText().toString());
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), READ_CALL_LOG);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{READ_CALL_LOG}, PERMISSION_REQUEST_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean readContact = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (readContact) {
                        mPresenter.readCallLogTime(fromDate.getText().toString(),toDate.getText().toString());
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(READ_CALL_LOG)) {
                                showMessageOKCancel("You need to allow access to the permission",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{READ_CALL_LOG},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }
                break;
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onCallDataReceived(MainData mainData) {
        localCalls.setText(mainData.getLocalCalls());
        totalCalls.setText(mainData.getTotalCalls());
        internationalCalls.setText(mainData.getInternationalCalls());
        remainingCalls.setText(mainData.getRemainingCalls());
    }

    @Override
    public void onDateReceived(MainData mainData) {
        fromDate.setText(mainData.getFromDate());
        toDate.setText(mainData.getToDate());
    }

    @Override
    public void onLastCheckedReceived(String str) {
        if (str.equals(""))
            head.setVisibility(View.GONE);
        else {
            /*long checkedTime = Long.valueOf(str);
            Date date = new Date(checkedTime);
            DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");
            String strDate = dateFormat.format(date);*/
            head.setVisibility(View.VISIBLE);
            lastchecked.setText(str);
        }
    }

}
