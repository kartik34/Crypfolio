package com.kartiksinghal.www.crypfolio;

import org.json.JSONObject;

public class NewsDataModel {

    private String mSource;
    private String mTitle;
    private String mDescription;
    private String mURL;
    private String mPublishDate;


    public static NewsDataModel fromJson(JSONObject jsonObject) {

        NewsDataModel newsData = new NewsDataModel();
        return newsData;

    }

    public String getmSource() {
        return mSource;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmURL() {
        return mURL;
    }

    public String getmPublishDate() {
        return mPublishDate;
    }
}