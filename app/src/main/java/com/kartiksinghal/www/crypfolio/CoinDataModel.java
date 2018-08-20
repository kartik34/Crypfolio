package com.kartiksinghal.www.crypfolio;

import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Currency;
public class CoinDataModel {

    private String mPrice;
    private String mPercentChange;
    private String mDollarChange;
    private static String Currency;
    private static String Crypto;

    public static CoinDataModel setConstants(String curr, String cryp){


        Currency = curr;
        Crypto = cryp;

        CoinDataModel constantModel = new CoinDataModel();

        return constantModel;
    }

    @Nullable
    public static CoinDataModel fromJSON(JSONObject jsonObject) {


        try {



            CoinDataModel coinData = new CoinDataModel();
            Log.d("DATA2", Currency);


            coinData.mPrice = Double.toString(jsonObject.getJSONObject("RAW").getJSONObject(Crypto).getJSONObject(Currency).getInt("PRICE"));

            Log.d("specific", "Price is: " + jsonObject.getJSONObject("RAW").getJSONObject(Crypto).getJSONObject(Currency).getInt("PRICE"));
            coinData.mPercentChange = Integer.toString(Math.round(jsonObject.getJSONObject("RAW").getJSONObject(Crypto).getJSONObject(Currency).getInt("CHANGEPCT24HOUR")));

            coinData.mDollarChange = Integer.toString(Math.round(jsonObject.getJSONObject("RAW").getJSONObject(Crypto).getJSONObject(Currency).getInt("CHANGE24HOUR")));




            return coinData;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;


        }



    }
    public String getmPrice () {
        return mPrice;
    }

    public String getmDollarChange() {
        return mDollarChange;
    }

    public String getmPercentChange () {
        return mPercentChange;
    }

}