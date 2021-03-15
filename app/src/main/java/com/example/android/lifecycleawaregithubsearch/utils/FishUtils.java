package com.example.android.lifecycleawaregithubsearch.utils;

import android.net.Uri;

import com.example.android.lifecycleawaregithubsearch.FishSearchAdapter;
import com.example.android.lifecycleawaregithubsearch.data.FishData;
import com.example.android.lifecycleawaregithubsearch.data.FishSearchResults;
import com.example.android.lifecycleawaregithubsearch.data.GitHubRepo;
import com.example.android.lifecycleawaregithubsearch.data.GitHubSearchResults;
import com.google.gson.Gson;

import java.util.ArrayList;

public class FishUtils {
    private final static String FISH_SEARCH_BASE_URL = "https://www.fishwatch.gov/api/species";
    private final static String GITHUB_SEARCH_QUERY_PARAM = "q";


    public static String buildGitHubSearchURL(String query) {
        if (query.equals("all") || query.equals("All") || query.equals("")) {
            return Uri.parse(FISH_SEARCH_BASE_URL).buildUpon().build().toString();
        } else {
            return Uri.parse(FISH_SEARCH_BASE_URL).buildUpon().appendQueryParameter("/", query).build().toString();
        }
    }


    public static ArrayList<FishData> parseResults(String json) {
        Gson gson = new Gson();
        FishSearchResults results = gson.fromJson(json, FishSearchResults.class);
        return results != null ? (results.items) : null;
    }
}
