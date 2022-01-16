package com.example.sbdprojekt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.sbdprojekt.toBuyList.ToBuyListActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements RecipeViewInterface{
    JSONArray ingredientsArray=new JSONArray();
    LinkedList<Recipe> recipeList=new LinkedList<>();

    static Recipe recipe;
    public Button menu_button;
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

        menu_button=findViewById(R.id.menu_button);
        search_button=findViewById(R.id.search_button);
        list_button=findViewById(R.id.list_button);
        recycler_View=findViewById(R.id.recyclerView);
        input_plainText =findViewById(R.id.input_plainText);

        final RecepieListAdapter[] listAdapter = {new RecepieListAdapter(this, recipeList,this)};
        recycler_View.setAdapter(listAdapter[0]);
        recycler_View.setLayoutManager(new LinearLayoutManager(this));

        //przypisanie funkcji przyciskom
        menu_button.setOnClickListener(view -> {
            //api request(treść przepisu)
            String url ="https://api.spoonacular.com/recipes/" + recipeId[0] + "/information?includeNutrition=false&apiKey=2f9e491e548a4435b945d8242f429033";
            JsonObjectRequest request = null;

            request=(new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    ingredientsArray=null;
                    try {
                        response.getJSONArray("extendedIngredients");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "response.toString()", Toast.LENGTH_SHORT).show();
                }
            }));

            RequestQueueSingleton.getInstance(MainActivity.this).addToRequestQueue(request);
        });

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

                    for (int i=0; i < 5; i++) {
                        try {

                            JSONObject ingredient = response.getJSONArray("results").getJSONObject(i);

                            recipeList.add(
                                    new Recipe(ingredient.getString("title"),
                                    ingredient.getString("sourceUrl"),
                                    ingredient.getString("image"),
                                    ingredient.getInt("id")));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

//        list_button.setOnClickListener(view -> startActivity(MainActivity.this, ToBuyListActivity.class));

    }

    @Override
    public void onItemClick(int position) {
        recipe=recipeList.get(position);

        startActivity(new Intent(this,RecipeActivity.class));
    }


}