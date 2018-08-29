package com.kartiksinghal.www.crypfolio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
    private NewsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    String mURL;
    String mTitle;
    String mImageUrl;
    String mDescription;
    String mTags;
    String mDate;
    String mSource;
    String apiKey = "bf9c5e28cf4c4d55b6ec53f000123fec";
    final String URL = "https://newsapi.org/v2/everything";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);


    }
    @Override
    protected void onResume() {


        super.onResume();
        Log.d("Debug", "onResume() called");
        String Coin;
        Intent myIntent = getIntent();
        Log.d("DEBUG", "new coin: " + myIntent.getStringExtra("Coin"));


        Coin = myIntent.getStringExtra("Coin");

        getCoinData(Coin);
    }
    private void getCoinData(String coin){

        Log.d("debug", "getCoinData() called");
        RequestParams params = new RequestParams();
        params.put("q", "+crypto+'"+coin+"'");
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

                parseJson(response);

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

            ArrayList<NewsItem> newsList = new ArrayList<>();

            for(int count = 0 ; count < json.getJSONArray("articles").length(); count++){

                Log.d("debug", "JSON : "+ json.getJSONArray("articles").getJSONObject(count).getString("title"));
                mTitle = json.getJSONArray("articles").getJSONObject(count).getString("title");
                mDescription = json.getJSONArray("articles").getJSONObject(count).getString("description");
                mDate = json.getJSONArray("articles").getJSONObject(count).getString("publishedAt");
                mSource = json.getJSONArray("articles").getJSONObject(count).getJSONObject("source").getString("name");
                mImageUrl = json.getJSONArray("articles").getJSONObject(count).getString("urlToImage");
                mURL = json.getJSONArray("articles").getJSONObject(count).getString("url");
                Log.d("btctest", mTitle);
                Log.d("btctest", mImageUrl);
                if (mImageUrl.equals("null")) {
                    mImageUrl = "https://i.imgur.com/t85MLGg.png";

                }else if(mImageUrl.equals("")){
                    mImageUrl = "https://i.imgur.com/t85MLGg.png";
                }
                else{
                    String check = mImageUrl.substring(0,4);
                    if(!check.equals("http")){
                        mImageUrl = "https://i.imgur.com/t85MLGg.png";
                    }
                }


                Log.d("btctest", mTitle);
                Log.d("btctest", mImageUrl);
                newsList.add(new NewsItem(mURL,mTitle, mImageUrl, mDescription, "edee", mDate, mSource));


            }

            mRecyclerView= findViewById(R.id.recyclerViewNews);
            mLayoutManager = new LinearLayoutManager(this);
            mAdapter = new NewsAdapter(newsList);

            mRecyclerView.setLayoutManager(mLayoutManager);

            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setOnNewsClickListener(new NewsAdapter.OnNewsClickListener() {
                @Override
                public void onNewsClick(String url) {
                    Toast.makeText(NewsData.this, "Redirecting to Article...", Toast.LENGTH_SHORT).show();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                }
            });


        }catch (JSONException e) {
            e.printStackTrace();


        }


    }




}

