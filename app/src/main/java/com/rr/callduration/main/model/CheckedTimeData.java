package com.rr.callduration.main.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Property;

@Entity(nameInDb="checkedtime")
public class CheckedTimeData {
    @Property(nameInDb="lastCheckedDetails")
    private long lastCheckedDetails;

    @Generated(hash = 628538904)
    public CheckedTimeData(long lastCheckedDetails) {
        this.lastCheckedDetails = lastCheckedDetails;
    }

    @Generated(hash = 757006548)
    public CheckedTimeData() {
    }

    public long getLastCheckedDetails() {
        return lastCheckedDetails;
    }

    public void setLastCheckedDetails(long lastCheckedDetails) {
        this.lastCheckedDetails = lastCheckedDetails;
    }
}

