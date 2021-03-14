package com.example.android.lifecycleawaregithubsearch.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GitHubRepo implements Serializable {
//    @SerializedName("full_name")
//    public String fullName;
//
//    public String description;
//
    @SerializedName("html_url")
    public String htmlUrl;
//
//    @SerializedName("stargazers_count")
//    public int stars;

    public String name;

    public String scientific_name;

    public String description;

    public String population;

    public String location;

    public String status;

    public String habitat;
}
