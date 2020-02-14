package com.example.myfavoriteapp;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABEL = "movie";

    public static final class  MovieColumns implements BaseColumns{
        public static String JUDUL ="title";
        public static String DESKRIPSI = "overview";
        public static String RILIS = "release_date";
        public static String FOTO = "image_poster";
        public static String POPULARITY = "popularity";
        public static String VOTE_AVERAGE = "vote_average";
    }

    public static final String AUTHORITY = "package com.example.myfavoriteapp";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABEL)
            .build();

    public static int getColumnInt(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName){
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    public static String getColumnString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }
}
