package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    private static final String SANDWICH_NAME = "name";
    private static final String SANDWICH_NAME_MAIN_NAME = "mainName";
    private static final String SANDWICH_NAME_ALSO = "alsoKnownAs";
    private static final String SANDWICH_NAME_PLACE_ORIGIN = "placeOfOrigin";
    private static final String SANDWICH_NAME_DESCRIPTION = "description";
    private static final String SANDWICH_NAME_IMAGE = "image";
    private static final String SANDWICH_NAME_INGREDIENTS = "ingredients";

    private static String mainName = null;
    private static String placeOfOrigin = null;
    private static String description = null;
    private static String image = null;
    private static ArrayList<String> ingredient = new ArrayList<>();
    private static ArrayList<String> alsoKnownAsArray = new ArrayList<>();

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject object = new JSONObject(json);

            // get the name object of the JSON String-Array
            JSONObject name = object.getJSONObject(SANDWICH_NAME);

            // get mainName object from the name JSONObject
            mainName = name.getString(SANDWICH_NAME_MAIN_NAME);

            // get alsoKnownAs array from the name JSONObject
            JSONArray alsoKnownAs = name.getJSONArray(SANDWICH_NAME_ALSO);

            for (int i = 0; i < alsoKnownAs.length(); i++) {
                alsoKnownAsArray.add(alsoKnownAs.getString(i));
            }

            // get placeOfOrigin object from the JSON String-Array
            placeOfOrigin = object.getString(SANDWICH_NAME_PLACE_ORIGIN);

            // get description object from the JSON String-Array
            description = object.getString(SANDWICH_NAME_DESCRIPTION);

            // get image object from the JSON String-Array
            image = object.getString(SANDWICH_NAME_IMAGE);

            // get ingredients object from the JSON String-Array
            JSONArray ingredients = object.getJSONArray(SANDWICH_NAME_INGREDIENTS);
            for (int i = 0; i < ingredients.length(); i++) {
                ingredient.add(ingredients.getString(i));
            }

            return new Sandwich(mainName, alsoKnownAsArray, placeOfOrigin, description, image, ingredient);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
