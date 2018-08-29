package com.kartiksinghal.www.crypfolio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class onStartController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.onstartmenu);

        final EditText editTextField = findViewById(R.id.nameOfCoin2);

        editTextField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                String coin = editTextField.getText().toString();

                Intent newCoinIntent = new Intent(onStartController.this, MainActivity.class);
                newCoinIntent.putExtra("Coin", coin );
                startActivity(newCoinIntent);

                return false;
            }
        });

    }




}
