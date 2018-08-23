package com.kartiksinghal.www.crypfolio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CoinAddController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_coin);

        final EditText editTextField = findViewById(R.id.nameOfCoin);
        Button backButton = findViewById(R.id.backButton);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coin = "";

                Intent newCoinIntent = new Intent(CoinAddController.this, MainActivity.class);
                newCoinIntent.putExtra("Coin", coin );
                startActivity(newCoinIntent);


                finish(); //destroys the ChangeCityController activity and goes back to the weatherControllerLayout
            }
        });

        editTextField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                String coin = editTextField.getText().toString();

                Intent newCoinIntent = new Intent(CoinAddController.this, MainActivity.class);
                newCoinIntent.putExtra("Coin", coin );
                startActivity(newCoinIntent);

                return false;
            }
        });

    }




}

