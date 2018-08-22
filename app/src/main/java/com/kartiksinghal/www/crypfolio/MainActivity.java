package com.kartiksinghal.www.crypfolio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    final String URL = "https://min-api.cryptocompare.com/data/pricemultifull";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String COIN = "coin";
    TextView mName;
    TextView mPercentChange;
    TextView mPrice;
    TextView mDollarChange;

    private String mCoinPrice;
    private String mCoinPercentChange;
    private String mCoinDollarChange;


    int count = 0;
    String params = "";
    ArrayList<String> coinsArrayList = new ArrayList<>();


    private String cryptoCoin; //string version of coinsArrayList
    final String currency = "USD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mName =  findViewById(R.id.mName);
        mPercentChange =  findViewById(R.id.mPercentChange);
        mPrice =  findViewById(R.id.mPrice);
        mDollarChange = findViewById(R.id.mDollarChange);



    }

    @Override
    protected void onResume() {


        super.onResume();
        Log.d("Debug", "onResume() called");


        loadData();
        Intent myIntent = getIntent();

        String Coin = myIntent.getStringExtra("Coin");

        if(Coin != null){

            saveData(Coin);
            loadData();
            updateUI(coinsArrayList);


        }else if(coinsArrayList.get(0) != "" && coinsArrayList.get(0) != null){
            updateUI(coinsArrayList);

        }

    }
    private void getCoinData(String coin){

        Log.d("debug", "getCoinData() called");
        RequestParams params = new RequestParams();
        params.put("fsyms", coin);
        params.put("tsyms", currency);

        CoinDataModel constantModel = CoinDataModel.setConstants(currency, coin);
        connect(params);
    }

    private void connect(RequestParams params){  //connect to CryptoCompare API and retrieve json body

        AsyncHttpClient client = new AsyncHttpClient();

        Log.d("debug", "connect() called");

        client.get(URL, params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){

                Log.d("Debug", "onSuccess() called");

                Log.d("Debug", "API Response: " + response.toString());

                parseJson(response);


//                if(test(response)){
//                    Log.d("currencies", "hello");
//                    loadData();
//                    coinsArrayList.remove(coinsArrayList.size()-1);
//                    Intent myIntent;
//                    myIntent = new Intent(MainActivity.this, CoinAddController.class);
//                    startActivity(myIntent);
//
//                    Toast.makeText(MainActivity.this, "Invalid coin (Must be all caps and coin code)", Toast.LENGTH_LONG).show();
//
//                }else{
//
//                    Log.d("currencies", "hello");
//
//
//                    //Update the UI by passing dataModel object
//
//                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response){
                Log.e("Debug", "Fail"+ e.toString());
                Log.d("Debug", "Status code " + statusCode);
                Toast.makeText(MainActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();

            }

        });

    }

    public void updateUI(ArrayList<String> list) { //update ui will feature all coins and data, not just one



        for (String x : list) {
            count++;
            Log.d("debug", x);

            if (count == 1) {
                params = x;

            } else {
                params = params + "," + x;
            }


        }

        getCoinData(params);


    }

    public void parseJson(JSONObject json){

        try{
            Log.d("debug", "parseJson() called");
            Log.d("debug", "JSON : "+ json.toString());

            loadData();
            Log.d("debug", "price: " + Double.toString(json.getJSONObject("RAW").getJSONObject(coinsArrayList.get(0)).getJSONObject(currency).getDouble("PRICE")));
//
//            Log.d("debug", mCoinPrice);


            ArrayList<coinItem> coinList = new ArrayList<>();


            for(String i : coinsArrayList){

                mCoinPrice = Double.toString(json.getJSONObject("RAW").getJSONObject(i).getJSONObject(currency).getDouble("PRICE"));
                double d = json.getJSONObject("RAW").getJSONObject(i).getJSONObject(currency).getDouble("CHANGE24HOUR");
                BigDecimal bd = new BigDecimal(d);
                bd = bd.round(new MathContext(3));
                double rounded = bd.doubleValue();
                mCoinDollarChange = Double.toString(rounded);
                mCoinPercentChange = Double.toString(Math.round(json.getJSONObject("RAW").getJSONObject(i).getJSONObject(currency).getDouble("CHANGEPCT24HOUR")));

                coinList.add(new coinItem(mCoinPrice, mCoinPercentChange, mCoinDollarChange, i, currency));


            }
            mRecyclerView = findViewById(R.id.recyclerView);
            mLayoutManager = new LinearLayoutManager(this);
            mAdapter = new CoinAdapter(coinList);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
//            double d = json.getJSONObject("RAW").getJSONObject(Crypto).getJSONObject(Currency).getDouble("CHANGE24HOUR");
//            BigDecimal bd = new BigDecimal(d);
//            bd = bd.round(new MathContext(3));
//            double rounded = bd.doubleValue();
//            mCoinDollarChange = Double.toString(rounded);
//
//            Log.d("debug", "It works: " + mCoinPrice);

        }catch (JSONException e) {
            e.printStackTrace();


        }


    }

    //===================================================================================================================

    //                                  Don't touch

    //===================================================================================================================
    public void saveData(String coin){

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(coinsArrayList.contains(coin) == false){

            Log.d("debug", "saveData() called");
            if(cryptoCoin != "" && cryptoCoin != null){
                cryptoCoin = sharedPreferences.getString(COIN, "");

                cryptoCoin = cryptoCoin + "," + coin;
                editor.putString(COIN,  cryptoCoin);
                editor.apply();
                Toast.makeText(this, "Coin Saved", Toast.LENGTH_SHORT).show();

            }else{
                cryptoCoin = coin;
                editor.putString(COIN,  cryptoCoin);
                editor.apply();
                Toast.makeText(this, "Coin Saved", Toast.LENGTH_SHORT).show();
            }

        }


    }

    public void loadData(){

        Log.d("debug", "loadData() called");


        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        cryptoCoin = sharedPreferences.getString(COIN, "");

        Log.d("currencies" , cryptoCoin);

        coinsArrayList = convertToArray(cryptoCoin);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.crypfolio_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Toast.makeText(this, "Item clicked", Toast.LENGTH_SHORT).show();

        Intent myIntent;
        myIntent = new Intent(MainActivity.this, CoinAddController.class);
        startActivity(myIntent);

        return super.onOptionsItemSelected(item);
    }
    private String convertToString(ArrayList<String> list) {

        StringBuilder sb = new StringBuilder();
        String delim = "";
        for (String s : list)
        {
            sb.append(delim);
            sb.append(s);
            delim = ",";
        }
        return sb.toString();
    }
    private ArrayList<String> convertToArray(String string) {

        ArrayList<String> list = new ArrayList<String>(Arrays.asList(string.split(",")));
        return list;
    }
    public boolean test(JSONObject json){
        try{
            if(json.getString("Response") == "Error"){
                Log.d("debug", " failure test called");
                return true;
            }else{
                Log.d("debug", " success test called");

                return false;
            }
        }catch (JSONException e) {
            e.printStackTrace();
            return false;

        }
    }

}
//    public boolean test(JSONObject json){
//        try{
//            if(json.getString("Response") == "Error" || json.getJSONObject("RAW").getJSONObject(coinsArrayList.get(coinsArrayList.size() -1)) == null){
//                Log.d("debug", " test called");
//                return true;
//            }else{
//                Log.d("debug", " test called");
//
//                return false;
//            }
//        }catch (JSONException e) {
//            e.printStackTrace();
//            return false;
//
//        }
//    }
//
//    public void updateUI(ArrayList<String> list) { //update ui will feature all coins and data, not just one
//
//        ArrayList<coinItem> coinList = new ArrayList<>();
//
//
//        for (String x : list) {
//            count++;
//            Log.d("debug", x);
//
//            if (count == 1) {
//                params = x;
//
//            } else {
//                params = params + "," + x;
//            }
//
//
//        }
//
//        getCoinData(params);
//
//
//        coinList.add(new coinItem(mCoinPrice, mCoinPercentChange, mCoinDollarChange, crypto, currency));
//
//
//        //this line adds a new coinItem Object containing all the attributes of the coin into the coinlist array.
//
//        mRecyclerView = findViewById(R.id.recyclerView);
//        mLayoutManager = new LinearLayoutManager(this);
//        mAdapter = new CoinAdapter(coinList);
//
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setAdapter(mAdapter);
//    }