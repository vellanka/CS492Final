package com.example.android.lifecycleawaregithubsearch.data;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImageGallery implements Serializable {
    @SerializedName("src")
    public String image_source;

    @SerializedName("alt")
    public String alternate_title;


}
