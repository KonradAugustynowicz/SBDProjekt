package com.example.sbdprojekt.ToBuyList.Ingredients;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.sbdprojekt.R;
import com.example.sbdprojekt.Recipe.RecipeStorage;

public class IngredientsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    String ingredients = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        Bundle extras = getIntent().getExtras();
        recyclerView = findViewById(R.id.recyclerView);

        IngredientsStorage.setIngredients(RecipeStorage.getRecipe(extras.getInt("id")).getIngredients(),RecipeStorage.getRecipe(extras.getInt("id")).getName());

        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(this,extras.getInt("id"));

        recyclerView.setAdapter(ingredientsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}