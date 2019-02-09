package com.rr.callduration.main.model;

public class MainData {
    String fromDate,toDate,localCalls, totalCalls, internationalCalls, remainingCalls, lastCheckedTime;

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getLocalCalls() {
        return localCalls;
    }

    public void setLocalCalls(String localCalls) {
        this.localCalls = localCalls;
    }

    public String getTotalCalls() {
        return totalCalls;
    }

    public void setTotalCalls(String totalCalls) {
        this.totalCalls = totalCalls;
    }

    public String getInternationalCalls() {
        return internationalCalls;
    }

    public void setInternationalCalls(String internationalCalls) {
        this.internationalCalls = internationalCalls;
    }

    public String getRemainingCalls() {
        return remainingCalls;
    }

    public void setRemainingCalls(String remainingCalls) {
        this.remainingCalls = remainingCalls;
    }

    public String getLastCheckedTime() {
        return lastCheckedTime;
    }

    public void setLastCheckedTime(String lastCheckedTime) {
        this.lastCheckedTime = lastCheckedTime;
    }
}
