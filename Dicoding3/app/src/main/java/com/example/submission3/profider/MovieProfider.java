package com.example.submission3.profider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.submission3.db.DatabaseContract;
import com.example.submission3.db.FavoriteHelper;

import java.util.Objects;
import static android.provider.ContactsContract.SyncState.CONTENT_URI;
import static com.example.submission3.db.DatabaseContract.TABLE_FAV;

public class MovieProfider  extends ContentProvider {
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final String AUTHORITY = "com.example.submission3.profider";
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private FavoriteHelper favoriteHelper;

    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_FAV, MOVIE);
        sUriMatcher.addURI(AUTHORITY, TABLE_FAV + "/#", MOVIE_ID);
    }


    @Override
    public boolean onCreate() {
        favoriteHelper = FavoriteHelper.getInstance(getContext());
        favoriteHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        favoriteHelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = favoriteHelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = favoriteHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long added;
        switch (sUriMatcher.match(uri)){
            case MOVIE:
                added= favoriteHelper.insertProvider(values);
                break;
                default:
                    added=0;
                    break;
        }
        if(added>0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return uri.parse(CONTENT_URI+"/"+added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleted;
        switch (sUriMatcher.match(uri)){
            case MOVIE_ID:
                deleted = favoriteHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        if (deleted>0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
       int updated;
       switch (sUriMatcher.match(uri)){
           case  MOVIE_ID:
                   updated = favoriteHelper.updateProvider(uri.getLastPathSegment(),values);
           break;
           default:
               updated= 0;
               break;
       }
       if (updated>0){
           getContext().getContentResolver().notifyChange(uri,null);
       }
       return updated;
    }

}
