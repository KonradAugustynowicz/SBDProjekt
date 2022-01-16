package com.example.sbdprojekt;

import java.util.ArrayList;
import java.util.List;

public class RecipeStorage {
    private static final RecipeStorage recipeStorage = new RecipeStorage();
    private List<Recipe> recipes= new ArrayList<>();

    public RecipeStorage() {
    }

    public List<Recipe> getRecipes(){
        return recipes;
    }

    public void getRecipe(int id){
        for (Recipe recipe:recipes){
//            if (recipe.getId()=id) return recipe;
        }
    }

}
