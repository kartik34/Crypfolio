package com.kartiksinghal.www.crypfolio;

import org.json.JSONObject;

public class CoinDataModel {

    private int mPrice;
    private String mName;
    private int mPercentChange;


    public static CoinDataModel fromJson(JSONObject jsonObject){

        CoinDataModel weatherData = new CoinDataModel();



        return weatherData;

    }


    public int getmPrice() {
        return mPrice;
    }

    public String getmName() {
        return mName;
    }

    public int getmPercentChange() {
        return mPercentChange;
    }
}
