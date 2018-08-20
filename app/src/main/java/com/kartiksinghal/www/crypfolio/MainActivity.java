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
import org.w3c.dom.Text;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    final String URL = "https://min-api.cryptocompare.com/data/pricemultifull";

    TextView mName;
    TextView mPercentChange;
    TextView mPrice;
    TextView mDollarChange;


    String crypto;
    final String currency = "USD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addCoinButton = findViewById(R.id.addCoinButton);

        mName =  findViewById(R.id.mName);
        mPercentChange =  findViewById(R.id.mPercentChange);
        mPrice =  findViewById(R.id.mPrice);
        mDollarChange = findViewById(R.id.mDollarChange);


        addCoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent;
                myIntent = new Intent(MainActivity.this, CoinAddController.class);
                startActivity(myIntent);
            }
        });


    }

    @Override
    protected void onResume() {


        super.onResume();
        Log.d("Debug", "onResume() called");


        Intent myIntent = getIntent();

        String Coin = myIntent.getStringExtra("Coin");

        if(Coin != null){


            getCoinData(Coin);

        }





    }
    private void getCoinData(String coin){

        RequestParams params = new RequestParams();
        params.put("fsyms", coin);
        params.put("tsyms", currency);

        crypto = coin;

        CoinDataModel constantModel = CoinDataModel.setConstants(currency, coin);

        connect(params);
    }

    private void connect(RequestParams params){  //connect to CryptoCompare API and retrieve json body

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(URL, params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){

                Log.d("Debug", "Success: " + response.toString());


                CoinDataModel coinData = CoinDataModel.fromJSON(response); //use constructor to create dataModel object


                updateUI(coinData); //Update the UI by passing dataModel object

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



        mName.setText(crypto);
        String price = "$" + coin.getmPrice() + " USD";
        mPrice.setText(price);

        String percentChange = "Δ24h " + coin.getmPercentChange() + "%";

        mPercentChange.setText(percentChange);

        String dollarChange = "Δ24h $" + coin.getmDollarChange();

        mDollarChange.setText(dollarChange);


    }
}
