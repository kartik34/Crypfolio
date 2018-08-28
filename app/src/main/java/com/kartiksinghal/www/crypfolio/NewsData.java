package com.kartiksinghal.www.crypfolio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.collect.Lists;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class NewsData extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    String mURL;
    String mTitle;
    String mImageUrl;
    String mDescription;
    String mTags;
    String mDate;
    String apiKey = "bf9c5e28cf4c4d55b6ec53f000123fec";
    final String URL = "https://newsapi.org/v2/everything";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);
        ArrayList<NewsItem> newsList = new ArrayList<>();
        newsList.add(new NewsItem("feerrf","Bitcoin rocks", "ewfewfw", "Blah blah blah", "edee", "sept 1st", "techcrunch.com"));
        newsList.add(new NewsItem("feerrf","Ripple rising", "ewfewfw", "This and that and this", "edee", "sept 2st", "recode.com"));
        newsList.add(new NewsItem("feerrf","Tesla announces crpytocurrency to compete with Bitcoin ", "ewfewfw", "Many are questioning Musk's sanity on this one...", "Tesla, Bitcoin", "sept 2st", "recode.com"));

        mRecyclerView= findViewById(R.id.recyclerViewNews);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new NewsAdapter(newsList);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);



    }
    @Override
    protected void onResume() {


        super.onResume();
//        Log.d("Debug", "onResume() called");
//        String Coin;
//        Intent myIntent = getIntent();
//        Log.d("DEBUG", "new coin: " + myIntent.getStringExtra("Coin"));
//
//
//        Coin = myIntent.getStringExtra("Coin");
//
//        getCoinData(Coin);
    }
    private void getCoinData(String coin){

        Log.d("debug", "getCoinData() called");
        RequestParams params = new RequestParams();
        params.put("q", "crypto+"+coin);
        params.put("language", "en");
        params.put("sortBy", "publishedAt");

        params.put("apiKey", apiKey);



        connect(params);
    }
    private void connect(RequestParams params){  //connect to CryptoCompare API and retrieve json body

        AsyncHttpClient client = new AsyncHttpClient();

        Log.d("debug", "connect() called");

        client.get(URL, params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){

//                parseJson(response);
                Log.d("debug", response.toString());

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response){
                Log.e("Debug", "Fail"+ e.toString());
                Log.d("Debug", "Status code " + statusCode);
                Toast.makeText(NewsData.this, "Request Failed", Toast.LENGTH_SHORT).show();

            }

        });

    }
    public void parseJson(JSONObject json){

        try{
            Log.d("debug", "parseJson() called");
            Log.d("debug", "JSON : "+ json.toString());

            Log.d("debug", "price: " + Double.toString(json.getJSONObject("RAW").getJSONObject("erfe").getJSONObject("erfe").getDouble("PRICE")));


            ArrayList<coinItem> newsList = new ArrayList<>();


            for(int i =0; i<20; i++){

               i++;


            }
//            mRecyclerView = findViewById(R.id.recyclerView);
//            mLayoutManager = new LinearLayoutManager(this);
//            mAdapter = new CoinAdapter(coinList);
//            mRecyclerView.setLayoutManager(mLayoutManager);
//            mRecyclerView.setAdapter(mAdapter);
//            mAdapter.setOnItemClickListener(new CoinAdapter.OnItemClickListener() {
//

//                @Override
//                public void onTitleLink(String name) {
//                      Intent news;
//                      news = new Intent(NewsData.this, NewsData.class);
//                      news.putExtra("Coin", name );
//                      startActivity(news);
//                }
//            });


        }catch (JSONException e) {
            e.printStackTrace();


        }


    }




}

