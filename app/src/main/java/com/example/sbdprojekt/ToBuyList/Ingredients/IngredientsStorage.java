package com.example.sbdprojekt.ToBuyList.Ingredients;

import com.example.sbdprojekt.Recipe.Recipe;
import com.example.sbdprojekt.Recipe.RecipeStorage;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class IngredientsStorage {
    private static final IngredientsStorage ingredientsStorage= new IngredientsStorage();
    private static String recipeName = "";
    private static LinkedList<Ingredient> ingredients = new LinkedList<>();

    private IngredientsStorage() {
        if (ingredientsStorage ==  null){
        }
    }

    public static IngredientsStorage getIngredientsStorage() {
        return ingredientsStorage;
    }

    public static LinkedList<Ingredient> getIngredients() {
        return ingredients;
    }

    public static void setIngredients(LinkedList<String> ingredients,String recipeName) {
        if (!IngredientsStorage.recipeName.equals(recipeName))
        {
            IngredientsStorage.recipeName=recipeName;
            IngredientsStorage.ingredients.clear();
            for (String ingredient:ingredients) {
                IngredientsStorage.ingredients.add(new Ingredient(ingredient));
            }
        }
    }
}
