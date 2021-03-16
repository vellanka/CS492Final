package com.example.android.lifecycleawaregithubsearch.data;

import android.text.TextUtils;
import android.util.Log;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FishSearchRepository {
    private static final String TAG = FishSearchRepository.class.getSimpleName();
    //private static final String BASE_URL = "http://www.bloowatch.org";
    private static final String BASE = "https://www.fishwatch.gov/api/";

    private MutableLiveData<List<FishData>> searchResults;
    private MutableLiveData<LoadingStatus> loadingStatus;

    private String currentQuery;

    //need new service here
   // private GitHubService gitHubService;
    private FishService fishService;

    public FishSearchRepository() {
        this.searchResults = new MutableLiveData<>();
        this.searchResults.setValue(null);

        this.loadingStatus = new MutableLiveData<>();
        this.loadingStatus.setValue(LoadingStatus.SUCCESS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.fishService = retrofit.create(FishService.class);
    }

    public LiveData<List<FishData>> getSearchResults() {
        return this.searchResults;
    }

    public LiveData<LoadingStatus> getLoadingStatus() {
        return this.loadingStatus;
    }

    public void loadSearchResults(String query) {
        if (shouldExecuteSearch(query)) {
            this.currentQuery = query;
            this.searchResults.setValue(null);
            this.loadingStatus.setValue(LoadingStatus.LOADING);
            Log.d(TAG, "running new search for this query: " + query);

            //Call<FishSearchResults> results = this.fishService.searchRepos(query);
            Call<List<FishData>> results = this.fishService.searchRepos(query);
            Log.d(TAG, "printing results: " + results.toString());
           // results.enqueue(new Callback<FishSearchResults>() {
            results.enqueue(new Callback<List<FishData>>() {
                @Override
                public void onResponse(Call<List<FishData>> call, Response<List<FishData>> response) {
                    if (response.code() == 200) {
                        Log.d(TAG, "successful response");
                        searchResults.setValue(response.body());
                        loadingStatus.setValue(LoadingStatus.SUCCESS);
                    } else {
                        Log.d(TAG, "unsuccessful response");
                        loadingStatus.setValue(LoadingStatus.ERROR);
                    }
                }

                @Override
                public void onFailure(Call<List<FishData>> call, Throwable t) {
                    t.printStackTrace();
                    loadingStatus.setValue(LoadingStatus.ERROR);
                }
            });
/*                @Override
            //    public void onResponse(Call<FishSearchResults> call, Response<FishSearchResults> response) {
                    if (response.code() == 200) {
                        Log.d(TAG, "suc cessful response");
                        searchResults.setValue(response.body().items);
                        loadingStatus.setValue(LoadingStatus.SUCCESS);
                    } else {
                        Log.d(TAG, "unsuccessful response");
                        loadingStatus.setValue(LoadingStatus.ERROR);
                    }
                }

                @Override
            //    public void onFailure(Call<FishSearchResults> call, Throwable t) {
                    t.printStackTrace();
                    loadingStatus.setValue(LoadingStatus.ERROR);
                }
            });*/
        } else {
            Log.d(TAG, "using cached results for this query: " + query);
        }
    }

    private boolean shouldExecuteSearch(String query) {
        return !TextUtils.equals(query, this.currentQuery)
                || this.loadingStatus.getValue() == LoadingStatus.ERROR;
    }
}
