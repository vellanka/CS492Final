package com.example.android.lifecycleawaregithubsearch.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class FishData implements Serializable {

    @SerializedName("Species Name")
    public String species_name;

    @SerializedName("Habitat")
    public String habitat;
/*
    @SerializedName("Location")
    public String location;

    @SerializedName("NOAA Fisheries Region")
    public String fisheries_region;

    @SerializedName("Population")
    public String population;

    @SerializedName("Scientific Name")
    public String scientific_name;

    @SerializedName("Availability")
    public String availability;

    @SerializedName("Calories")
    public String calories;

    @SerializedName("Carbohydrate")
    public String carbs;

    @SerializedName("Cholesterol")
    public String cholesterol;

    @SerializedName("Physical Description")
    public String physical_description;

    @SerializedName("Health Benefits")
    public String health_benefits;

    @SerializedName("Protein")
    public String protein;

    @SerializedName("Taste")
    public String taste;

    @SerializedName("Texture")
    public String texture;

    @SerializedName("Quote")
    public String quote;

    @SerializedName("Biology")
    public String biology;*/
}
