package com.example.sbdprojekt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.sbdprojekt.Recipe.RecepieListAdapter;
import com.example.sbdprojekt.Recipe.Recipe;
import com.example.sbdprojekt.Recipe.RecipeActivity;
import com.example.sbdprojekt.Recipe.RecipeViewInterface;
import com.example.sbdprojekt.ToBuyList.ToBuyListActivity;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements RecipeViewInterface {
    JSONArray ingredientsArray=new JSONArray();
    LinkedList<Recipe> recipeList=new LinkedList<>();
    public Button search_button;
    public Button list_button;
    public EditText input_plainText;
    RecyclerView recycler_View;

    public String[] recepieNames= new String[5];
    public String[] links=new String[5];

    public MainActivity() {
        // Context
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String[] recipeId = new String[1];

        search_button=findViewById(R.id.search_button);
        list_button=findViewById(R.id.list_button);
        recycler_View=findViewById(R.id.recyclerView);
        input_plainText =findViewById(R.id.input_editText);

        final RecepieListAdapter[] listAdapter = {new RecepieListAdapter(this, recipeList,this)};
        recycler_View.setAdapter(listAdapter[0]);
        recycler_View.setLayoutManager(new LinearLayoutManager(this));

        //przypisanie funkcji przyciskom
        search_button.setOnClickListener(view -> {

            //Api request(znajdź przepis)
            JsonObjectRequest request = null;
            String url ="https://api.spoonacular.com/recipes/search?query="+ input_plainText.getText().toString() + "&number=5&apiKey=2f9e491e548a4435b945d8242f429033";

            request = (new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        recipeId[0] = response.getJSONArray("results").getJSONObject(0).getString("id"); //w getJsonObject numerek przepisu
                        Toast.makeText(MainActivity.this, response.getJSONArray("results").getJSONObject(0).getString("title"), Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, recipeId[0], Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    recipeList.clear();

                    try {
                        int length=response.getJSONArray("results").length();
                        if (length!=0) {
                            for (int i=0; i < length; i++) {
                                JSONObject ingredient = response.getJSONArray("results").getJSONObject(i);

                                recipeList.add(
                                        new Recipe(ingredient.getString("title"),
                                                ingredient.getString("sourceUrl"),
                                                ingredient.getString("image"),
                                                ingredient.getInt("id")));
                            }
                        }else{
                             View contextView = findViewById(R.id.search_button) ;
                             Snackbar.make(contextView, R.string.no_recipe_found, Snackbar.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    listAdapter[0] = new RecepieListAdapter(MainActivity.this,recipeList,MainActivity.this);
                    recycler_View.setAdapter(listAdapter[0]);
                    recycler_View.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "response.toString()", Toast.LENGTH_SHORT).show();
                }
            }));

            RequestQueueSingleton.getInstance(MainActivity.this).addToRequestQueue(request);
        });

        list_button.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ToBuyListActivity.class));
        });

    }

    @Override
    public void onItemClick(int position) {
        Recipe recipe=recipeList.get(position);
        Intent intent=new Intent(this, RecipeActivity.class);
        intent.putExtra("apiId",recipe.getApiId());
        intent.putExtra("imageApi",recipe.getImageApi());
        intent.putExtra("link",recipe.getLink());
        intent.putExtra("name",recipe.getName());
        startActivity(intent);
    }


}