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

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    final String URL = "https://min-api.cryptocompare.com/data/pricemultifull";

    TextView mName;
    TextView mPercentChange;
    TextView mPrice;
    TextView mDollarChange;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String COIN = "coin";

    private String cryptoCoin;

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


        loadData();
        Intent myIntent = getIntent();

        String Coin = myIntent.getStringExtra("Coin");

        if(Coin != null){
            saveData(Coin);
            loadData();
            getCoinData(cryptoCoin);


        }else if(cryptoCoin != "" && cryptoCoin != null){
            getCoinData(cryptoCoin);

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

                if(coinData == null){

                    Intent myIntent;
                    myIntent = new Intent(MainActivity.this, CoinAddController.class);
                    startActivity(myIntent);

                    Toast.makeText(MainActivity.this, "Invalid coin (Must be all caps and coin code)", Toast.LENGTH_LONG).show();

                }else{
                    updateUI(coinData); //Update the UI by passing dataModel object

                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response){
                Log.e("Debug", "Fail"+ e.toString());
                Log.d("Debug", "Status code " + statusCode);
                Toast.makeText(MainActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();

            }

        });

    }

    private void updateUI(CoinDataModel coin){ //update ui will feature all coins and data, not just one

        ArrayList<coinItem> coinList = new ArrayList<>();
        Log.d("debug", crypto + currency);
        coinList.add(new coinItem(coin.getmPrice(), coin.getmPercentChange(), coin.getmDollarChange(), CoinDataModel.getCrypto(), CoinDataModel.getCurrency()));
        coinList.add(new coinItem(coin.getmPrice(), coin.getmPercentChange(), coin.getmDollarChange(), CoinDataModel.getCrypto(), CoinDataModel.getCurrency()));

        //this line adds a new coinItem Object containing all the attributes of the coin into the coinlist array.

        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new CoinAdapter(coinList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

//         mName.setText(crypto);
//        String price = "$" + coin.getmPrice() + " USD";
//        mPrice.setText(price);
//
//        String percentChange = "Δ24h " + coin.getmPercentChange() + "%";
//
//        mPercentChange.setText(percentChange);
//
//        String dollarChange = "Δ24h $" + coin.getmDollarChange();
//
//        mDollarChange.setText(dollarChange);
//        // ADD NEW SECTION HERE ONLY

    }
    public void saveData(String coin){

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(COIN,  coin);
        editor.apply();
        Toast.makeText(this, "Coin Saved", Toast.LENGTH_SHORT).show();

    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        cryptoCoin = sharedPreferences.getString(COIN, "");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.crypfolio_menu, menu);
        return true;
    }
}
