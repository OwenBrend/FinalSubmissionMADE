package com.example.submission3.viewModel;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.submission3.model.Movie;
import com.example.submission3.rest.ApiClient;
import com.example.submission3.rest.ApiInterface;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.submission3.BuildConfig.API_KEY;

public class MoviesViewModel extends ViewModel {
    private ApiInterface mApiInterface;
    private MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();

    public void setMovies () {
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        try {
            Call<String> authorized = mApiInterface.getDiscoverMovie(API_KEY, "en-US");
            final ArrayList<Movie> listItem = new ArrayList<>();

            authorized.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        try {

                            JSONObject responseObject = new JSONObject(response.body());
                            JSONArray list = responseObject.getJSONArray("results");

                            for (int i = 0; i < list.length(); i++) {
                                JSONObject weather = list.getJSONObject(i);
                                Movie movieItems = new Movie(weather);
                                listItem.add(movieItems);
                            }
                            listMovies.postValue(listItem);
                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                }
            });
        } catch (Exception e) {
            System.out.println("error" + e);
        }
    }


    public void setSearchMovie(String query) {
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        try {
            Call<String> authorized = mApiInterface.getSearchMovie(API_KEY, "en-US", query);
            final ArrayList<Movie> listItems = new ArrayList<>();
            authorized.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {

                        try {
                            JSONObject responseObject = new JSONObject(response.body());
                            JSONArray list = responseObject.getJSONArray("results");

                            for (int i = 0; i < list.length(); i++) {
                                JSONObject movie = list.getJSONObject(i);
                                Movie movies = new Movie(movie);
                                listItems.add(movies);
                            }
                            listMovies.postValue(listItems);
                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());

                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable throwable) {
                }
            });
        } catch (Exception e) {
            System.out.println("error" + e);
        }

    }

    public LiveData<ArrayList<Movie>> getMovie() {
        return listMovies;
    }

    public LiveData<ArrayList<Movie>> getSearchMovies() {
        return listMovies;
    }
}


