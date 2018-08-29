package com.kartiksinghal.www.crypfolio;

public class coinItem {

    private String mPrice;
    private String mPercentChange;
    private String mDollarChange;
    private String mName;
    private String mCurrency;

    public coinItem(String mPrice, String mPercentChange, String mDollarChange, String mName, String mCurrency) {
        this.mPrice = mPrice;
        this.mPercentChange = mPercentChange;
        this.mDollarChange = mDollarChange;
        this.mName = mName;
        this.mCurrency = mCurrency;
    }

    public String getmPrice() {
        return mPrice;
    }

    public String getmPercentChange() {
        return mPercentChange;
    }

    public String getmDollarChange() {
        return mDollarChange;
    }

    public String getmName() {
        return mName;
    }

    public String getmCurrency() {
        return mCurrency;
    }
}
