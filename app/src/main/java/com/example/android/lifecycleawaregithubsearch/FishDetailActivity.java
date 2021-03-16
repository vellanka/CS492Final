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
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.lifecycleawaregithubsearch.data.FishData;
import com.example.android.lifecycleawaregithubsearch.data.GitHubRepo;

import org.w3c.dom.Text;

public class FishDetailActivity extends AppCompatActivity {
    public static final String EXTRA_FISHDATA = "FishData";

    private static final String TAG = FishDetailActivity.class.getSimpleName();

    private Toast errorToast;

    private FishData fishData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO
        //MAKE LAYOUT
        setContentView(R.layout.activity_repo_detail);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_FISHDATA)) {
            this.fishData = (FishData)intent.getSerializableExtra(EXTRA_FISHDATA);
            //Log.d(TAG, "Got repo with name: " + repo.name);

            //TextView repoNameTV = findViewById(R.id.tv_repo_name);
           // TextView repoStarsTV = findViewById(R.id.tv_repo_stars);
            //TextView repoDescriptionTV = findViewById(R.id.tv_repo_description);

            TextView fishnameTV = findViewById(R.id.fish_name);
            TextView scientificnameTV = findViewById(R.id.scientific_name);
            TextView backgroundColor = findViewById(R.id.background_color);
            TextView population = findViewById(R.id.population);
            TextView fishingRate = findViewById(R.id.fishingRate);
            TextView location = findViewById(R.id.location);

            //some color hex codes come in without the # at the start, add if it is missing
            if(fishData.background_color.charAt(0) != '#'){
                fishData.background_color = '#' + fishData.background_color;
            }



            location.setText(getString(R.string.location) + " " + parseHtml(fishData.location));
            fishingRate.setText(getString(R.string.fishing_rate) + " " + fishData.fishingRate);
            backgroundColor.setBackgroundColor(Color.parseColor(fishData.background_color));
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.repo_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                shareRepo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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