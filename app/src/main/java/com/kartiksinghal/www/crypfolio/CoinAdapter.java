package com.kartiksinghal.www.crypfolio;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;



public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.CoinViewHolder> {

    private ArrayList<coinItem> mCoinList;
    private  OnItemClickListener mListener;


    public interface OnItemClickListener{
        void onItemClick(String name);
        void onCoinClick(String name);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class CoinViewHolder extends RecyclerView.ViewHolder{

        public TextView mName;
        public TextView mPercentChange;
        public TextView mPrice;
        public TextView mDollarChange;
        public ImageView mDeleteImage;
        public LinearLayout mCoinView;




        public CoinViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            mName =  itemView.findViewById(R.id.mName);
            mPercentChange =  itemView.findViewById(R.id.mPercentChange);
            mPrice =  itemView.findViewById(R.id.mPrice);
            mDollarChange = itemView.findViewById(R.id.mDollarChange);
            mDeleteImage = itemView.findViewById(R.id.image_delete);
            mCoinView = itemView.findViewById(R.id.coinItem);

            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(listener != null){
                        String name = mName.getText().toString();

                        listener.onItemClick(name);
                    }


//                    Intent newCoinIntent = new Intent(CoinAdapter.this, MainActivity.class);
//                    newCoinIntent.putExtra("Coin", coin );
//                    startActivity(newCoinIntent);

                }
            });
            mCoinView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        String name = mName.getText().toString();
                        Log.d("touch", name);
                        listener.onCoinClick(name);


                    }
                }
            });
        }

    }

    public CoinAdapter(ArrayList<coinItem> coinList){
        mCoinList = coinList;
    }

    @NonNull
    @Override
    public CoinViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.coinitem, viewGroup, false);
        CoinViewHolder evh = new CoinViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull CoinViewHolder coinViewHolder, int i) {

        coinItem currentCoin = mCoinList.get(i);
        if(Double.parseDouble(currentCoin.getmDollarChange()) < 0){
            coinViewHolder.mDollarChange.setTextColor(Color.BLUE);
            String dollarChange = "Δ24  $" + currentCoin.getmDollarChange() + " " + currentCoin.getmCurrency();
            coinViewHolder.mDollarChange.setText(dollarChange);

        }
        else{
            String dollarChange = "Δ24  +$" + currentCoin.getmDollarChange() + " " + currentCoin.getmCurrency();
            coinViewHolder.mDollarChange.setText(dollarChange);
            coinViewHolder.mDollarChange.setTextColor(Color.RED);

        }
        if(Double.parseDouble(currentCoin.getmPercentChange()) < 0){
            String percentChange = "Δ24  " + currentCoin.getmPercentChange() + "%";
            coinViewHolder.mPercentChange.setText(percentChange);
            coinViewHolder.mPercentChange.setTextColor(Color.BLUE);

        }
        else{
            String percentChange = "Δ24  +" + currentCoin.getmPercentChange() + "%";
            coinViewHolder.mPercentChange.setText(percentChange);
            coinViewHolder.mPercentChange.setTextColor(Color.RED);

        }

        coinViewHolder.mName.setText(currentCoin.getmName());

        String price = "$" + currentCoin.getmPrice() + " " +  currentCoin.getmCurrency();
        coinViewHolder.mPrice.setText(price);



    }


    @Override
    public int getItemCount() {
        return mCoinList.size();
    }
}
