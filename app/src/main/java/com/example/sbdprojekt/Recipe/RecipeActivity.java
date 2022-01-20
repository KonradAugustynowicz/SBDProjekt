package com.example.sbdprojekt.Recipe;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.sbdprojekt.Fragments.DescriptionFragment;
import com.example.sbdprojekt.Fragments.IngredientsFragment;
import com.example.sbdprojekt.Fragments.ProgressFragment;
import com.example.sbdprojekt.R;
import com.example.sbdprojekt.RequestQueueSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class RecipeActivity extends AppCompatActivity {
    private RecipeStorage recipeStorage;
    String ingredientsFromApi = "";
    public Recipe recipe;

    public TextView title;
    public TextView link;
    public ImageView recipeImage;
    public Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Bundle extras = getIntent().getExtras();

        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.description_fragment,new ProgressFragment());
        fragmentTransaction.commit();

        fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ingredients_fragment,new ProgressFragment());
        fragmentTransaction.commit();


        recipe = new Recipe(extras.getString("name"),
                extras.getString("link"),
                extras.getString("imageApi"),
                extras.getInt("apiId"));

        title = findViewById(R.id.title_textView);
        link = findViewById(R.id.link_textView);
        recipeImage = findViewById(R.id.recipe_image);
        addButton = findViewById(R.id.add_button);

        title.setText(recipe.name);
        link.setText(Html.fromHtml("<a href='" + recipe.link + "'> link </a>"));
        link.setMovementMethod(LinkMovementMethod.getInstance());

        String url = "https://api.spoonacular.com/recipes/" + recipe.apiId + "/information?includeNutrition=false&apiKey=2f9e491e548a4435b945d8242f429033";
        JsonObjectRequest request = null;

        //ładowanie obrazka
        RequestQueueSingleton.getInstance(this).getImageLoader().get(
                "https://spoonacular.com/recipeImages/" + recipe.imageApi,
                new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        recipeImage.setImageBitmap(response.getBitmap());
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //request do api(skłądniki oraz opis przepisu)
        request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    LinkedList<JSONObject> ingredientsList = new LinkedList<>();
                    recipe.description = response.getString("summary");
                    int i = 0;
                    while (response.getJSONArray("extendedIngredients").length() > i) {
                        recipe.ingredients.add(response.getJSONArray("extendedIngredients").getJSONObject(i).getString("original"));
                        i++;
                    }
                    i = 0;

                    while (recipe.ingredients.size() > i) {
                        ingredientsFromApi += recipe.ingredients.get(i) + "\n\n";
                        i++;
                    }
                    addButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            RecipeStorage.putRecipe(recipe);
                        }
                    });
                    replaceDescriptionFragment(new DescriptionFragment(R.id.description_fragment,recipe.description));
                    replaceIngredientsFragment(new IngredientsFragment(R.id.ingredients_fragment,ingredientsFromApi));
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
        //koniec części z api



    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putInt("apiId",recipe.getApiId());
        outState.putString("imageApi",recipe.getImageApi());
        outState.putString("link",recipe.getLink());
        outState.putString("name",recipe.getName());
        super.onSaveInstanceState(outState);
    }

    public void replaceDescriptionFragment(Fragment fragment){
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.description_fragment,fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void replaceIngredientsFragment(Fragment fragment){
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ingredients_fragment,fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

}