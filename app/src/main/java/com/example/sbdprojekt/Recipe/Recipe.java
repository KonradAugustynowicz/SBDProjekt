package com.example.sbdprojekt.Recipe;

import android.content.Context;
import android.os.Parcelable;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.sbdprojekt.ToBuyList.Ingredients.Ingredient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.UUID;

public class Recipe{
    public int id;
    int apiId;
    String name;
    String link;
    String imageApi;
    String description;
    LinkedList<Ingredient> ingredients=new LinkedList<>();


    public Recipe(String name, String link,String imageApi,int apiId) {
        this.name = name;
        this.link = link;
        this.imageApi = imageApi;
        this.apiId = apiId;
    }

    public Recipe(Recipe recipe) {
        this.apiId = recipe.apiId;
        this.name = recipe.name;
        this.link = recipe.link;
        this.imageApi = recipe.imageApi;
        this.description = recipe.description;
        this.apiId = recipe.apiId;
        ingredients = recipe.ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApiId() {
        return apiId;
    }

    public void setApiId(int apiId) {
        this.apiId = apiId;
    }

    public String getImageApi() {
        return imageApi;
    }

    public void setImageApi(String imageApi) {
        this.imageApi = imageApi;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LinkedList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(LinkedList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
