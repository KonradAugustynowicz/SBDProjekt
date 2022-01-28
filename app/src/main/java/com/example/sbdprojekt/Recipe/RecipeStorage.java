package com.example.sbdprojekt.Recipe;

import com.example.sbdprojekt.ToBuyList.Ingredients.Ingredient;

import java.util.HashSet;
import java.util.Set;

public class RecipeStorage {
    private static final RecipeStorage recipeStorage = new RecipeStorage();
    private static Set<Recipe> recipes= new HashSet<>();
    private static int id=0;

    private RecipeStorage() {
    }

    public static Set<Recipe> getRecipes(){
        return recipes;
    }

    public static Recipe getRecipe(int id){

        for (Recipe recipe:recipes){
            if (recipe.getId()==id) return recipe;
        }
        return null;
    }

    public static void putRecipe(Recipe recipe){
        for (Recipe recipe1:recipes) {
            if(recipe1.getName().equals(recipe.getName()))
                return;
        }
        Recipe tempRecipe = new Recipe(recipe);
        tempRecipe.setId(id);
        id++;
        recipes.add(tempRecipe);
    }

    public static void removeRecipe(Recipe recipe){
        recipes.remove(RecipeStorage.recipes.remove(recipe));
        id=0;
        for (Recipe recipe1:recipes){
            recipe1.setId(id);
            id++;
        }
    }

}
