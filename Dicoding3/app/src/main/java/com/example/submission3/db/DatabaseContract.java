package com.example.submission3.db;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_FAV = "fav";
    static String TABLE_FAV2 = "fav2";

    static final class NoteColumns implements BaseColumns {
        static String TITLE = "title";
        static String DATE = "date";
        static String RATING = "rating";
        static String POPULARITY = "popularity";
        static String COUNT = "count";
        static String LANGUAGE = "language";
        static String IMAGE = "image";
        static String IMAGE2 = "image2";
        static String OVERVIEW = "overview";
    }
}