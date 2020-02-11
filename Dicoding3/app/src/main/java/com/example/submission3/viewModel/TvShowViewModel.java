package com.example.submission3.viewModel;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.submission3.model.TVShow;

import com.example.submission3.rest.ApiClient;
import com.example.submission3.rest.ApiInterface;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.submission3.BuildConfig.API_KEY;


public class TvShowViewModel extends ViewModel {
    private ApiInterface mApiInterface;
    private MutableLiveData<ArrayList<TVShow>> listTvShow = new MutableLiveData<>();



    public void setTVShow (final String tvShow){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TVShow> listItem = new ArrayList<>();

        String url ="https://api.themoviedb.org/3/tv/top_rated?api_key="+ API_KEY+"&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list =responseObject.getJSONArray("results");

                    for (int i = 0; i<list.length();i++){
                        JSONObject tv = list.getJSONObject(i);
                        TVShow tvShowItem = new TVShow(tv);
                        listItem.add(tvShowItem);
                    }
                    listTvShow.postValue(listItem);
                }catch(Exception e){
                    Log.d("Exception",e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure",error.getMessage());
            }
        });
    }

    public void setSearchTvShow(String query) {
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        try {
            Call<String> autorized = mApiInterface.getSearchTvShow(API_KEY,"en-US",query);
            final ArrayList<TVShow> listItems = new ArrayList<>();
            autorized.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful()){
                        try {
                            JSONObject responseObject = new JSONObject(response.body());
                            JSONArray list = responseObject.getJSONArray("results");

                            for (int i =0; i<list.length();i++){
                                JSONObject tvShow = list.getJSONObject(i);
                                TVShow tvShows = new TVShow(tvShow);
                                listItems.add(tvShows);
                            }
                            listTvShow.postValue(listItems);
                        }
                        catch (Exception e){
                            Log.d("Exception", e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }catch (Exception e){
            System.out.println("error" + e);
        }
    }

    public LiveData<ArrayList<TVShow>> getTvShow() {
        return listTvShow;
    }

    public  LiveData<ArrayList<TVShow>> getSearchTvShow() {
        return listTvShow;
    }


}