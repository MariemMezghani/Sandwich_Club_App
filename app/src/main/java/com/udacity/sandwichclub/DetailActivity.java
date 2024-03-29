package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                // returns image from drawable if image defined in load() not found
                .error(R.drawable.shawarma)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    // method handles data binding to the views
    private void populateUI(Sandwich sandwich) {
        // find text views that will be displayed
        TextView otherNames = findViewById(R.id.also_known_tv);
        TextView otherNamesLabel = findViewById(R.id.also_known_label);
        TextView origin = findViewById(R.id.origin_tv);
        TextView originLabel = findViewById(R.id.origin_lab);
        TextView description = findViewById(R.id.description_tv);
        TextView ingredients = findViewById(R.id.ingredients_tv);

        //bind the views with the json parsed data
        description.setText(sandwich.getDescription());
        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            origin.setVisibility(View.GONE);
            originLabel.setVisibility(View.GONE);
        } else {
            origin.setText(sandwich.getPlaceOfOrigin());
        }
        List<String> otherNamesList = sandwich.getAlsoKnownAs();
        if (otherNamesList.isEmpty() == true) {
            otherNames.setVisibility(View.GONE);
            otherNamesLabel.setVisibility(View.GONE);

        } else {
            String onl = presentList(otherNamesList);
            otherNames.setText(onl);
        }
        List<String> ingredientsList = sandwich.getIngredients();
        String il = presentList(ingredientsList);
        ingredients.setText(il);

    }

    // method returns elements of the list seperately
    private String presentList(List<String> list) {
        String string = "";
        for (String i : list) {
            string += "- " + i + "\n";
        }
        return string;
    }
}
