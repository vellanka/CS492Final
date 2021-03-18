package com.example.android.lifecycleawaregithubsearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.lifecycleawaregithubsearch.data.FishData;
import com.example.android.lifecycleawaregithubsearch.OnSwipeTouchListener;
import com.example.android.lifecycleawaregithubsearch.data.GitHubRepo;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class FishDetailActivity extends AppCompatActivity  {
    public static final String EXTRA_FISHDATA = "FishData";
    public static String favFish;
    private static final String TAG = FishDetailActivity.class.getSimpleName();
    private Toast favorite;

    private Toast errorToast;
    private boolean isFavorite;
    private FishData fishData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO
        //MAKE LAYOUT
        setContentView(R.layout.activity_repo_detail);
        isFavorite = false;


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_FISHDATA)) {
            this.fishData = (FishData)intent.getSerializableExtra(EXTRA_FISHDATA);
            //Log.d(TAG, "Got repo with name: " + repo.name);
            //Log.d(TAG, "This is the info we got from Image Gallery: " + this.fishData.imageGalleries[0].alternate_title );
            //Log.d(TAG, "This is the image url we got from Image Gallery: " + this.fishData.imageGalleries[0].image_source );
            //TextView repoNameTV = findViewById(R.id.tv_repo_name);
           // TextView repoStarsTV = findViewById(R.id.tv_repo_stars);
            //TextView repoDescriptionTV = findViewById(R.id.tv_repo_description);

            TextView fishnameTV = findViewById(R.id.fish_name);
            TextView scientificnameTV = findViewById(R.id.scientific_name);
            TextView backgroundColor = findViewById(R.id.background_color);
            ImageView sourceImage = findViewById(R.id.source_image);
            TextView population = findViewById(R.id.population);
            TextView fishingRate = findViewById(R.id.fishingRate);
            TextView location = findViewById(R.id.location);

            //some color hex codes come in without the # at the start, add if it is missing
            if(fishData.background_color.charAt(0) != '#'){
                fishData.background_color = '#' + fishData.background_color;
            }



            location.setText(getString(R.string.location) + " " + parseHtml(fishData.location));
            fishingRate.setText(getString(R.string.fishing_rate) + " " + fishData.fishingRate);
            //Some of the image galleries contain nothing
            backgroundColor.setBackgroundColor(Color.parseColor(fishData.background_color));
            if (fishData.imageGalleries.image_source != null) {
                Picasso.get().load(fishData.imageGalleries.image_source).resize(750, 0).into(sourceImage);
            }
            scientificnameTV.setBackgroundColor(Color.parseColor(fishData.background_color));
            fishnameTV.setBackgroundColor(Color.parseColor(fishData.background_color));
            fishnameTV.setText(fishData.species_name);
            scientificnameTV.setText("( " + fishData.scientific_name + " )");
            if (fishData.population == null){
                fishData.population = "No Data Available";
            }
            population.setText(getString(R.string.population_text) + " " +fishData.population);

            //replace HTML text with regular
            //repoNameTV.setText(repo.name);
//            repoStarsTV.setText(String.valueOf(repo.stars));
//            repoDescriptionTV.setText(repo.description);
        }
        RelativeLayout relativeLayout = findViewById(R.id.detail_main_activity);
        relativeLayout.setOnTouchListener(new OnSwipeTouchListener(FishDetailActivity.this) {
            public void onSwipeTop() {
                Toast.makeText(FishDetailActivity.this, "top", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSwipeRight() {
                Log.d(TAG, "creating new intent for: " + fishData.species_name );
                Intent intent1 = new Intent(FishDetailActivity.this, FishDescriptionActivity.class);
                intent1.putExtra(FishDescriptionActivity.EXTRA_FISHDATA, fishData);
                startActivity(intent1);
            }
            public void onSwipeLeft() {
                Toast.makeText(FishDetailActivity.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                Toast.makeText(FishDetailActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });

        if (MainActivity.sharedPreferences.getString("pref_favFish", "").equals(fishData.species_name)){
            isFavorite = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isFavorite){
            getMenuInflater().inflate(R.menu.repo_detail_favorite, menu);
        }
        else {
            getMenuInflater().inflate(R.menu.repo_detail, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                shareRepo();
                return true;
            case R.id.action_favorite:
                toggleFavorite(item);
              //  shareRepo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void toggleFavorite(MenuItem menuItem){


        if (this.fishData != null){
            this.isFavorite = !this.isFavorite;
            menuItem.setCheckable(this.isFavorite);
            if (this.isFavorite){
                menuItem.setIcon(R.drawable.ic_star_full);
                if (this.favorite != null){
                    this.favorite.cancel();
                }
                this.favorite = Toast.makeText(this, "You have just selected " + fishData.species_name + " as your favorite seafood!", Toast.LENGTH_LONG);
                this.favorite.show();

                favFish = this.fishData.species_name;
            }
            else{
                menuItem.setIcon(R.drawable.ic_baseline_star_border_24);
                if (this.favorite != null){
                    this.favorite.cancel();
                }
                this.favorite = Toast.makeText(this, "You have just removed " + fishData.species_name + " as your favorite...", Toast.LENGTH_LONG);
                this.favorite.show();
                favFish = "";

            }

            MainActivity.sharedPreferences.edit().putString("pref_favFish", favFish).apply();


        }
    }
    private void viewRepoOnWeb() {
        if (this.fishData != null) {
            Uri githubRepoUri = Uri.parse(this.fishData.species_name);
            Intent intent = new Intent(Intent.ACTION_VIEW, githubRepoUri);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                if (this.errorToast != null) {
                    this.errorToast.cancel();
                }
                this.errorToast = Toast.makeText(this, "Error...", Toast.LENGTH_LONG);
                this.errorToast.show();
            }
        }
    }

    private void shareRepo() {
        if (this.fishData != null) {
            String shareText = getString(
                    R.string.share_animal_text,
                    this.fishData.species_name
            );
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, shareText);
            intent.setType("text/plain");

            Intent chooserIntent = Intent.createChooser(intent, null);
            startActivity(chooserIntent);
        }
    }

    public String parseHtml(String input){
        String output = input;
        if (output == null){
            return "No Data Found";
        }
        output = output.replace("<li>","");
        output = output.replace("</li>","");
        output = output.replace("<p>","");
        output = output.replace("</p>","");
        output = output.replace("<a>","");
        output = output.replace("</a>","");
        output = output.replace("</>","");
        output = output.replace("<ul>","");
        output = output.replace("</ul>","");
        output = output.replace("<a href=","");
        output = output.replace(">","");
        output = output.replace("\n"," ");

        String delims = "\\.\\s";
        String []Sentences = output.split(delims);
        for (int i  = 0; i < Sentences.length; i++){
            if (Sentences[i].contains("https://")){
                output = output.replace(Sentences[i],"");
            }
        }
        if (output == null){
            output = "No Data Found";
        }
        return output;
    }

}