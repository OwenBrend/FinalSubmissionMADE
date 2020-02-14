package com.example.myfavoriteapp;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import static android.provider.MediaStore.MediaColumns.TITLE;
import static com.example.myfavoriteapp.DatabaseContract.MovieColumns.DESKRIPSI;
import static com.example.myfavoriteapp.DatabaseContract.MovieColumns.FOTO;
import static com.example.myfavoriteapp.DatabaseContract.MovieColumns.JUDUL;
import static com.example.myfavoriteapp.DatabaseContract.MovieColumns.POPULARITY;
import static com.example.myfavoriteapp.DatabaseContract.MovieColumns.RILIS;
import static com.example.myfavoriteapp.DatabaseContract.MovieColumns.VOTE_AVERAGE;
import static com.example.myfavoriteapp.DatabaseContract.getColumnInt;
import static com.example.myfavoriteapp.DatabaseContract.getColumnString;

public class MovieItem implements Parcelable {

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }



    private int id;
    private String photo;
    private String title;
    private String release;
    private Double vote_average;
    private Double popularity;
    private String detail;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(photo);
        dest.writeString(title);
        dest.writeString(release);
        dest.writeValue(vote_average);
        dest.writeValue(popularity);
        dest.writeString(detail);
        dest.writeInt(id);

    }

    public static final Creator<MovieItem> CREATOR = new Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel in) {
            return new MovieItem(in);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };

    public MovieItem(Parcel in) {
        photo = in.readString();
        title = in.readString();
        release = in.readString();
        vote_average= (Double) in.readValue(Double.class.getClassLoader());
        popularity = (Double) in.readValue(Double.class.getClassLoader());
        detail = in.readString();
        id= in.readInt();
    }
    public MovieItem (Cursor cursor){
        this.id = getColumnInt(cursor,_ID);
        this.title= getColumnString(cursor,JUDUL);
        this.photo = getColumnString(cursor,FOTO);
        this.release = getColumnString(cursor,RILIS);
        this.vote_average= Double.valueOf(getColumnString(cursor,VOTE_AVERAGE));
        this.popularity = Double.valueOf(getColumnString(cursor,POPULARITY));
        this.detail = getColumnString(cursor,DESKRIPSI);
    }

}
