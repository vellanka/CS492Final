package com.example.android.lifecycleawaregithubsearch.utils;

import android.net.Uri;

import com.example.android.lifecycleawaregithubsearch.data.GitHubRepo;
import com.example.android.lifecycleawaregithubsearch.data.GitHubSearchResults;
import com.google.gson.Gson;

import java.util.ArrayList;

public class GitHubUtils {
    private final static String GITHUB_SEARCH_BASE_URL = "http://www.bloowatch.org";
    private final static String GITHUB_SEARCH_QUERY_PARAM = "q";


    public static String buildGitHubSearchURL(String query) {
        return Uri.parse(GITHUB_SEARCH_BASE_URL).buildUpon()
                .appendQueryParameter(GITHUB_SEARCH_QUERY_PARAM, query)
                .build()
                .toString();
    }

    public static ArrayList<GitHubRepo> parseGitHubSearchResults(String json) {
        Gson gson = new Gson();
        GitHubSearchResults results = gson.fromJson(json, GitHubSearchResults.class);
        return results != null ? results.items : null;
    }
}
