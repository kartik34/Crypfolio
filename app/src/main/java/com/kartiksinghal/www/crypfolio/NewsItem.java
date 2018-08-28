package com.kartiksinghal.www.crypfolio;

public class NewsItem {

    private String mURL;
    private String mTitle;
    private String mImageUrl;
    private String mDescription;
    private String mTags;
    private String mDate;
    private String mSource;

    public NewsItem(String mURL, String mTitle, String mImageUrl, String mDescription, String mTags, String mDate, String mSource) {
        this.mURL = mURL;
        this.mTitle = mTitle;
        this.mImageUrl = mImageUrl;
        this.mDescription = mDescription;
        this.mTags = mTags;
        this.mDate = mDate;
        this.mSource = mSource;
    }

    public String getmURL() {
        return mURL;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmTags() {
        return mTags;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmSource() {
        return mSource;
    }
}
