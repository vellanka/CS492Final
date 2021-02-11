package com.example.android.lifecycleawaregithubsearch.data;

import android.util.Log;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GitHubSearchRepository {
    private static final String TAG = GitHubSearchRepository.class.getSimpleName();
    private static final String BASE_URL = "http://api.github.com";

    private MutableLiveData<List<GitHubRepo>> searchResults;

    private GitHubService gitHubService;

    public GitHubSearchRepository() {
        this.searchResults = new MutableLiveData<>();
        this.searchResults.setValue(null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.gitHubService = retrofit.create(GitHubService.class);
    }

    public LiveData<List<GitHubRepo>> getSearchResults() {
        return this.searchResults;
    }

    public void loadSearchResults(String query) {
        this.searchResults.setValue(null);
        Log.d(TAG, "running new search for this query: " + query);
        Call<GitHubSearchResults> results = this.gitHubService.searchRepos(query);
        results.enqueue(new Callback<GitHubSearchResults>() {
            @Override
            public void onResponse(Call<GitHubSearchResults> call, Response<GitHubSearchResults> response) {
                if (response.code() == 200) {
                    searchResults.setValue(response.body().items);
                }
            }

            @Override
            public void onFailure(Call<GitHubSearchResults> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
