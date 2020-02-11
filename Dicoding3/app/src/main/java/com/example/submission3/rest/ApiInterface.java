package com.example.submission3.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("discover/movie")
    Call<String> getDiscoverMovie(@Query("api_key") String API_KEY,
                                  @Query("language") String language);

    @GET("search/movie")
    Call<String> getSearchMovie(@Query("api_key") String API_KEY,
                                @Query("language") String language,
                                @Query("query") String keyword);

    @GET("discover/tv")
    Call<String> getDiscoverTv(@Query("api_key") String API_KEY,
                               @Query("language") String language);

    @GET("search/tv")
    Call<String> getSearchTvShow(@Query("api_key") String API_KEY,
                                     @Query("language") String language,
                                     @Query("query") String keyword);

    @GET("discover/movie")
    Call<String> getReleaseMovie(@Query("api_key") String API_KEY,
                                 @Query("primary_release_date.gte") String ReleaseDate,
                                 @Query("primary_release_date.lte") String TodayDate);
}
