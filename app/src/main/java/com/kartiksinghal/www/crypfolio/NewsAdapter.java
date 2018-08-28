package com.kartiksinghal.www.crypfolio;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private ArrayList<NewsItem> mNewsList;
    public static class NewsViewHolder extends RecyclerView.ViewHolder{

        public ImageView mNewsImage;
        public TextView mTitle;
        public TextView mDate;
        public TextView mDescription;
        public TextView mSource;



        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            mNewsImage.findViewById(R.id.mNewsImage);
            mTitle.findViewById(R.id.mTitle);
            mDate.findViewById(R.id.mDate);
            mDescription.findViewById(R.id.mDescription);
            mSource.findViewById(R.id.mSource);

        }
    }

    public NewsAdapter(ArrayList<NewsItem> newsList){

        mNewsList = newsList;

    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.newsitem, viewGroup, false);
        NewsViewHolder nvh =  new NewsViewHolder(v);
        return nvh;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int i) {
        //put data into news view page
        NewsItem currentItem = mNewsList.get(i);

        newsViewHolder.mTitle.setText(currentItem.getmTitle());
        newsViewHolder.mDescription.setText(currentItem.getmDescription());
        newsViewHolder.mSource.setText(currentItem.getmSource());



    }

    @Override
    public int getItemCount() {

        return mNewsList.size();
    }
}
