package com.example.sbdprojekt.ToBuyList.Ingredients;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sbdprojekt.MainActivity;
import com.example.sbdprojekt.R;
import com.example.sbdprojekt.Recipe.RecipeStorage;
import com.example.sbdprojekt.ToBuyList.ToBuyListActivity;

public class IngredientsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button deleteButton;
    String ingredients = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        Bundle extras = getIntent().getExtras();
        recyclerView = findViewById(R.id.recyclerView);
        deleteButton = findViewById(R.id.delete_button);

        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(this,extras.getInt("id"));

        recyclerView.setAdapter(ingredientsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecipeStorage.removeRecipe(RecipeStorage.getRecipe(extras.getInt("id")));
                IngredientsActivity.this.finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        startActivity(new Intent(IngredientsActivity.this, ToBuyListActivity.class));
    }
}