package com.example.myfavoriteapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.example.myfavoriteapp.DatabaseContract.MovieColumns.DESKRIPSI;
import static com.example.myfavoriteapp.DatabaseContract.MovieColumns.FOTO;
import static com.example.myfavoriteapp.DatabaseContract.MovieColumns.JUDUL;
import static com.example.myfavoriteapp.DatabaseContract.MovieColumns.POPULARITY;
import static com.example.myfavoriteapp.DatabaseContract.MovieColumns.RILIS;
import static com.example.myfavoriteapp.DatabaseContract.MovieColumns.VOTE_AVERAGE;
import static com.example.myfavoriteapp.DatabaseContract.getColumnString;

public class MovieAdapter extends CursorAdapter {

    public MovieAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cardview_movie,parent,false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if(cursor != null){
            TextView tvTitle;
            TextView tvOverview;
            TextView tvRelease;
            TextView tvPopularity;
            TextView tvCountAverage;
            ImageView imgPoster ;

            tvTitle = view.findViewById(R.id.txt_title);
            tvRelease = view.findViewById(R.id.txt_date);
            tvPopularity = view.findViewById(R.id.popularity);
            tvCountAverage = view.findViewById(R.id.tv_item_vote_average);
            tvOverview = view.findViewById(R.id.txt_detail);

            tvTitle.setText(getColumnString(cursor,JUDUL));
            tvOverview.setText(getColumnString(cursor,DESKRIPSI));
            tvRelease.setText(getColumnString(cursor,RILIS));
            tvPopularity.setText(getColumnString(cursor,POPULARITY));
            tvCountAverage.setText(getColumnString(cursor,VOTE_AVERAGE));
            imgPoster = view.findViewById(R.id.img_photo);
            Picasso.with(context).load("http://image.tmdb.org/t/p/w185/"+getColumnString(cursor, FOTO)).into(imgPoster);



        }
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

}
