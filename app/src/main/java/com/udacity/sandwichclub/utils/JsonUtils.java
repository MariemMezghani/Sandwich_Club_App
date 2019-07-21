package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            //convert json string to json object
            JSONObject sandwichJsonObject = new JSONObject(json);
            //parse sandwichJsonObject
            JSONObject name = sandwichJsonObject.getJSONObject("name");
            String mainName = name.getString("mainName");
            JSONArray otherNames = name.getJSONArray("alsoKnownAs");
            ArrayList<String> otherNamesList = new ArrayList<>();
            for (int i = 0; i < otherNames.length(); i++) {
                otherNamesList.add(otherNames.getString(i));
            }
            String placeOfOrigin = sandwichJsonObject.getString("placeOfOrigin");
            String description = sandwichJsonObject.getString("description");
            String image = sandwichJsonObject.getString("image");
            JSONArray ingredients = sandwichJsonObject.getJSONArray("ingredients");
            ArrayList<String> ingredientsList = new ArrayList<>();
            for (int i = 0; i < ingredients.length(); i++) {
                ingredientsList.add(ingredients.getString(i));
            }

            //return a new sandwich object with the parameters parsed from a json object
            Sandwich sandwich = new Sandwich(mainName, otherNamesList, placeOfOrigin, description, image, ingredientsList);
            return sandwich;


        } catch (JSONException e) {
            // print the error
            e.printStackTrace();
            return null;
        }


    }
}
