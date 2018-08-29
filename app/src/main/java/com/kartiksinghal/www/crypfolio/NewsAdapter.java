package com.kartiksinghal.www.crypfolio;
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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private ArrayList<NewsItem> mNewsList;
    private OnNewsClickListener mListener;

    public interface OnNewsClickListener{
        void onNewsClick(String url);
    }
    public void setOnNewsClickListener(OnNewsClickListener listener){
        mListener = listener;
    }
    private static ArrayList<String> url = new ArrayList<>();
    public static class NewsViewHolder extends RecyclerView.ViewHolder{

        public ImageView mNewsImage;
        public TextView mTitle;
        public TextView mDate;
        public TextView mDescription;
        public TextView mSource;
        public LinearLayout mNewsItem;
        public TextView mURL;



        public NewsViewHolder(@NonNull View itemView, final OnNewsClickListener listener) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.mTitle);
            mDate = itemView.findViewById(R.id.mDate);
            mDescription = itemView.findViewById(R.id.mDescription);
            mSource = itemView.findViewById(R.id.mSource);
            mNewsImage = itemView.findViewById(R.id.mNewsImage);
            mURL = itemView.findViewById(R.id.mURL);
            mNewsItem = itemView.findViewById(R.id.newsItem);

            mNewsItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        Log.d("debug", "url: "+ mURL.getText().toString());
                        listener.onNewsClick(mURL.getText().toString());
                    }
                }
            });


        }
    }

    public NewsAdapter(ArrayList<NewsItem> newsList){

        mNewsList = newsList;

    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.newsitem, viewGroup, false);
        NewsViewHolder nvh =  new NewsViewHolder(v, mListener);
        return nvh;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int i) {
        //put data into news view page
        NewsItem currentItem = mNewsList.get(i);

        newsViewHolder.mTitle.setText(currentItem.getmTitle());
        String description = "     " + currentItem.getmDescription();
        newsViewHolder.mDescription.setText(description);
        newsViewHolder.mSource.setText(currentItem.getmSource());

        String date = currentItem.getmDate().substring(0, 10);
        newsViewHolder.mDate.setText(date);

        newsViewHolder.mURL.setText(currentItem.getmURL());
        Picasso.get().load(currentItem.getmImageUrl()).transform(new RoundedCornersTransform()).into(newsViewHolder.mNewsImage);





    }

    @Override
    public int getItemCount() {

        return mNewsList.size();
    }
}
