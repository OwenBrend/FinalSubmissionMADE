package com.example.submission3.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.submission3.BuildConfig;
import com.example.submission3.R;
import com.example.submission3.db.FavoriteHelper;
import com.example.submission3.model.Movie;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

    private final Context mContext;
    private final FavoriteHelper favoriteHelper;
    private ArrayList<Movie> listMovies = new ArrayList<>();


    StackRemoteViewsFactory(Context context) {
        mContext = context;
        favoriteHelper = FavoriteHelper.getInstance(mContext);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        listMovies = favoriteHelper.getAllFavorites();
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return listMovies.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget);

        Bitmap bmp;
        try {
            //noinspection deprecation
            bmp = Glide.with(mContext)
                    .asBitmap()
                    .load( BuildConfig.TMDB_URL_POSTER + listMovies.get(position).getPhoto())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
            rv.setImageViewBitmap(R.id.imageView, bmp);
            rv.setTextViewText(R.id.textView_widget, listMovies.get(position).getTitle());
            Log.d("Widget", "Success");
        } catch (InterruptedException | ExecutionException e) {
            Log.d("Widget Load Error", "Error");
        }
        Bundle extras = new Bundle();
        extras.putInt(WidgetFavorit.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}