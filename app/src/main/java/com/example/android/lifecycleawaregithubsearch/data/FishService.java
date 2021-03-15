package com.example.android.lifecycleawaregithubsearch.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FishService {

    @GET("species/{q}")
   Call<FishSearchResults> searchRepos(@Path("q") String query);

   // @GET("species")
   // Call<FishSearchResults> searchRepos(String query);

}

