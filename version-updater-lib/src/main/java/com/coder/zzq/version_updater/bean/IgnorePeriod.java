package com.coder.zzq.version_updater.bean;


import static com.coder.zzq.version_updater.Constants.ALWAYS;
import static com.coder.zzq.version_updater.Constants.NEVER;

public class IgnorePeriod {
    private int mDays;
    private int mHours;
    private int mMinutes;
    private int mSeconds;


    private IgnorePeriod() {

    }


    public static int always() {
        return ALWAYS;
    }

    public static int never() {
        return NEVER;
    }


    public static IgnorePeriod create() {
        return new IgnorePeriod();
    }

    public IgnorePeriod days(int days) {
        mDays = days;
        return this;
    }

    public IgnorePeriod hours(int hours) {
        mHours = hours;
        return this;
    }

    public IgnorePeriod minutes(int minutes) {
        mMinutes = minutes;
        return this;
    }

    public IgnorePeriod seconds(int seconds) {
        mSeconds = seconds;
        return this;
    }


    public long combine() {
        return (mDays * 24 * 3600L) + (mHours * 3600L) + (mMinutes * 60L) + mSeconds;
    }
}
