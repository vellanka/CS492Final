package com.example.android.lifecycleawaregithubsearch.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubService {
    @GET("developers/json/species")
    Call<GitHubSearchResults> searchRepos(@Query("q") String query);

//    @GET("search/repositories/{q}")
//    Call<GitHubSearchResults> searchRepos(@Path("q") String query);
}
