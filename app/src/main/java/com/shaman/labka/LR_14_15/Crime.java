package com.shaman.labka.LR_14_15;

import java.util.Date;

public class Crime {

    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Crime() {
        mDate = new Date();
    }


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
