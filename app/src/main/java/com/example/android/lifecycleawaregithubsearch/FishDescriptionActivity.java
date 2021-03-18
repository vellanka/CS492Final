package com.example.android.lifecycleawaregithubsearch;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.lifecycleawaregithubsearch.data.FishData;

import org.w3c.dom.Text;

public class FishDescriptionActivity extends AppCompatActivity {
    public static final String EXTRA_FISHDATA = "FishData";

    private final String TAG= FishDescriptionActivity.class.getSimpleName();

    private FishData fishdata;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);

       setContentView(R.layout.activity_fish_description);

       Intent intent = getIntent();
       if (intent != null && intent.hasExtra(EXTRA_FISHDATA)){
           this.fishdata = (FishData)intent.getSerializableExtra(EXTRA_FISHDATA);
            //TODO location, description, scientific info
           //servings, servings weight, calories, total fat, fatty acids, cholesterol, sodium, carbs, fiber, sugars, protein
           TextView fishNameTV = findViewById(R.id.tv_fish_name);
           TextView servingsTV = findViewById(R.id.tv_servings);
           TextView servingsWeightTV = findViewById(R.id.tv_serving_size);
           TextView caloriesTV = findViewById(R.id.tv_calories);
           TextView fatTotalTV = findViewById(R.id.tv_fat_total);
           TextView fattyAcidsTV = findViewById(R.id.tv_fatty_acids);
           TextView cholesterolTV = findViewById(R.id.tv_cholesterol);
           TextView sodiumTV = findViewById(R.id.tv_sodium);
           TextView carbsTV = findViewById(R.id.tv_carbohydrate);
           TextView fiberTV = findViewById(R.id.tv_fiber);
           TextView sugarsTV = findViewById(R.id.tv_sugars);
           TextView proteinTV = findViewById(R.id.tv_protein);
           TextView backgroundColorTV = findViewById(R.id.background_color);
            TextView nutritionTextTV = findViewById(R.id.tv_nutrition_text);

           if(fishdata.background_color.charAt(0) != '#'){
               fishdata.background_color = '#' + fishdata.background_color;
           }

           Log.d(TAG, "onCreate: in the new intent: " + fishdata.physical_description);

           fishNameTV.setText(fishdata.species_name);
           fishNameTV.setBackgroundColor(Color.parseColor(fishdata.background_color));
           nutritionTextTV.setBackgroundColor(Color.parseColor(fishdata.background_color));
           servingsTV.setText("Number of servings: " + " " + fishdata.servings);
           servingsWeightTV.setText("Serving Size: " + fishdata.serving_weight);
           caloriesTV.setText("Calories: " + fishdata.calories);
           fatTotalTV.setText("Fats, Total: " + fishdata.fat);
           fattyAcidsTV.setText("Fatty Acids: " + fishdata.fattyacids_content);
           cholesterolTV.setText("Cholesterol: " + fishdata.cholesterol);
           sodiumTV.setText("Sodium: " + fishdata.sodium);
           carbsTV.setText("Carbohydrate, Total: " + fishdata.carbohydrate);
           fiberTV.setText("Fiber: " + fishdata.fiber);
           sugarsTV.setText("Sugars, Total: " + fishdata.sugars);
           proteinTV.setText("Protein: " + fishdata.protein_content);
           backgroundColorTV.setBackgroundColor(Color.parseColor(fishdata.background_color));
//           Log.d(TAG, "Word vomit incoming: " + fishdata.protein_content + ", "
//                   + fishdata.fattyacids_content + ", " +
//                   fishdata.selenium_content + ", " +
//                   fishdata.serving_weight + ", " +
//                   fishdata.servings + ", " +
//                   fishdata.sodium + ", " +
//                   fishdata.sugars + ", " +
//                   fishdata.taste + ", " +
//                   fishdata.texture + ", " +
//                   fishdata.cholesterol + ", " +
//                   fishdata.calories + ", " +
//                   fishdata.fat + ", " +
//                   fishdata.fiber + ", " );

           fishNameTV.setText(fishdata.species_name);

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
                shareNutrition();
                return true;
            case R.id.action_favorite:

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void shareNutrition() {
        if (this.fishdata != null) {
            String shareText = "Hey! Lets have some " + fishdata.species_name + " for dinner: There are " + fishdata.servings + " servings and " + fishdata.calories + " colories. The macronutrient values are: " + fishdata.protein_content + " protein, " + fishdata.carbohydrate + " carbs, and " + fishdata.fat + " fats. Enjoy!";

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, shareText);
            intent.setType("text/plain");

            Intent chooserIntent = Intent.createChooser(intent, null);
            startActivity(chooserIntent);
        }
    }

}
