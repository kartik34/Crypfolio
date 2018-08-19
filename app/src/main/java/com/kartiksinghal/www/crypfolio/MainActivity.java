package com.kartiksinghal.www.crypfolio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    final String URL = "hhttps://min-api.cryptocompare.com/data/price";

    TextView mName;
    TextView mPercentChange;
    TextView mPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button changeCityButton = findViewById(R.id.addCoinButton);

        mName =  findViewById(R.id.mName);
        mPercentChange =  findViewById(R.id.mPercentChange);
        mPrice =  findViewById(R.id.mPrice);

        changeCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, CoinAddController.class);
                startActivity(myIntent);
            }
        });

        addCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Debug", "onResume() called");

        Intent myIntent = getIntent();
        String Coin = myIntent.getStringExtra("Coin");

        getCoinData(Coin);




    }
    private void getCoinData(String name){

        RequestParams params = new RequestParams();
        params.put("fsym", name);
        params.put("tsyms", "USD");

        connect(params);
    }
    private void connect(RequestParams params){

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(URL, params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){

                Log.d("Debug", "Success: " + response.toString());

                CoinDataModel weatherData = CoinDataModel.fromJson(response);

                updateUI(weatherData);

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response){
                Log.e("Debug", "Fail"+ e.toString());
                Log.d("Debug", "Status code " + statusCode);
                Toast.makeText(MainActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();

            }

        });

    }

    private void updateUI(CoinDataModel coin){

        mPrice.setText(coin.getmPrice());

        mPercentChange.setText(coin.getmPercentChange());

        mName.setText(coin.getmName());


    }
}
