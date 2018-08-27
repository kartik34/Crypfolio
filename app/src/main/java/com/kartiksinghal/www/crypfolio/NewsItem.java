package com.kartiksinghal.www.crypfolio;

public class NewsItem {

    private String mURL;
    private String mTitle;
    private String mImageUrl;
    private String mBody;
    private String mTags;
    private String mDate;

    public NewsItem(String mURL, String mTitle, String mImageUrl, String mBody, String mTags, String mDate) {
        this.mURL = mURL;
        this.mTitle = mTitle;
        this.mImageUrl = mImageUrl;
        this.mBody = mBody;
        this.mTags = mTags;
        this.mDate = mDate;
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

    public String getmBody() {
        return mBody;
    }

    public String getmTags() {
        return mTags;
    }

    public String getmDate() {
        return mDate;
    }
}
