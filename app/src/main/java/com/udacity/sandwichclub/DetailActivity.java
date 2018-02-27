package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        int position = 0;
        if (intent != null) {
            position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        }
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
                .placeholder(R.drawable.sandwich_on_toast)
                .error(R.drawable.no_sandwich_image)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView name = findViewById(R.id.mainName);
        TextView alsoKnownAs = findViewById(R.id.also_known_tv);
        TextView origin = findViewById(R.id.origin_tv);
        TextView description = findViewById(R.id.description_tv);
        TextView ingredients = findViewById(R.id.ingredients_tv);

        name.setText(blankSpace(sandwich.getMainName()));
        origin.setText(blankSpace(sandwich.getPlaceOfOrigin()));
        description.setText(blankSpace(sandwich.getDescription()));

        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        StringBuilder output = new StringBuilder();
        for (String s : alsoKnownAsList) {
            output.append(s).append(", ");
        }

        if (output.length() > 0) {
            output = new StringBuilder(output.substring(0, output.length() - 2));
        }
        alsoKnownAs.setText(blankSpace(output.toString()));

        output = new StringBuilder();
        List<String> ingredientsList;
        ingredientsList = sandwich.getIngredients();
        for (String s : ingredientsList) {
            output.append(s).append("\n");
        }
        ingredients.setText(blankSpace(output.toString()));
    }

    private String blankSpace(String s) {
        if (s.equals("")) {
            return getString(R.string.no_info_found);
        } else {
            return s;
        }
    }
}
