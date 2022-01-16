package com.example.sbdprojekt;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.UUID;
public class RecipeActivity extends AppCompatActivity {
    public Recipe recipe;

    public TextView title;
    public TextView link;
    public TextView description;
    public TextView ingredients;
    public ImageView recipeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        title=findViewById(R.id.title_textView);
        link=findViewById(R.id.link_textView);
        description=findViewById(R.id.description_textView);
        ingredients=findViewById(R.id.ingredients_textView);
        recipeImage=findViewById(R.id.recipe_image);

        title.setText(MainActivity.recipe.name);
        link.setText(Html.fromHtml("<a href='" + MainActivity.recipe.link + "'> link </a>"));
        link.setMovementMethod(LinkMovementMethod.getInstance());

        String url ="https://api.spoonacular.com/recipes/" + MainActivity.recipe.apiId + "/information?includeNutrition=false&apiKey=2f9e491e548a4435b945d8242f429033";
        JsonObjectRequest request = null;

        RequestQueueSingleton.getInstance(this).getImageLoader().get(
                "https://spoonacular.com/recipeImages/"+ MainActivity.recipe.imageApi,
                new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        recipeImage.setImageBitmap(response.getBitmap());
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String ingredientsFromApi = "";
                    LinkedList<JSONObject> ingredientsList=new LinkedList<>();
                    MainActivity.recipe.description=response.getString("summary");
                    description.setText(Html.fromHtml(MainActivity.recipe.description));
                    int i=0;
                    while(response.getJSONArray("extendedIngredients").length()>i){
                        MainActivity.recipe.ingredients.add(response.getJSONArray("extendedIngredients").getJSONObject(i).getString("original"));
                        i++;
                    }
                    i=0;
                    while(MainActivity.recipe.ingredients.size()>i){
                        ingredientsFromApi+=MainActivity.recipe.ingredients.get(i) + "\n";
                        i++;
                    }
                    ingredients.setText(ingredientsFromApi);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        RequestQueueSingleton.getInstance(this).addToRequestQueue(request);
    }

}