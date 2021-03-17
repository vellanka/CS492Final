package com.example.android.lifecycleawaregithubsearch.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FishData implements Serializable {

    @SerializedName("Species Name")
    public String species_name;

    @SerializedName("Habitat")
    public String habitat;

    @SerializedName("Species Illustration Photo")
    public ImageGallery imageGalleries;


    @SerializedName("Location")
    public String location;

    /*
    @SerializedName("NOAA Fisheries Region")
    public String fisheries_region;
    */

    @SerializedName("Population")
    public String population;

    @SerializedName("Scientific Name")
    public String scientific_name;

    @SerializedName("Quote Background Color")
    public String background_color;

    //HTML
    @SerializedName("Population Status")
    public String populationStatus;

    @SerializedName("Fishing Rate")
    public String fishingRate;

    @SerializedName("Color")
    public String color;

    @SerializedName("Species Aliases")
    public String aliases;

    /*
    @SerializedName("Availability")
    public String availability;
*/

    @SerializedName("Protein")
    public String protein_content;

    @SerializedName("Saturated Fatty Acids, Total")
    public String fattyacids_content;

    @SerializedName("Selenium")
    public String selenium_content;

    @SerializedName("Serving Weight")
    public  String serving_weight;

    @SerializedName("Servings")
    public String servings;

    @SerializedName("Sodium")
    public String sodium;

    @SerializedName("Sugars, Total")
    public String sugars;

    @SerializedName("Taste")
    public String taste;

    @SerializedName("Texture")
    public String texture;

    @SerializedName("Cholesterol")
    public String cholesterol;

    @SerializedName("Calories")
    public String calories;

    @SerializedName("Fat, Total")
    public String fat;

    @SerializedName("Fiber, Total Dietary")
    public String fiber;

    @SerializedName("Carbohydrate")
    public String carbohydrate;


     //HTML
    @SerializedName("Physical Description")
    public String physical_description;


    @SerializedName("Quote")
    public String quote;

    @SerializedName("Biology")
    public String biology;
}
