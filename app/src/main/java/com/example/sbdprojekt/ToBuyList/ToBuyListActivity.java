package com.example.sbdprojekt.ToBuyList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.sbdprojekt.R;
import com.example.sbdprojekt.Recipe.RecipeStorage;
import com.example.sbdprojekt.Recipe.RecipeViewInterface;
import com.example.sbdprojekt.ToBuyList.Ingredients.IngredientsActivity;

public class ToBuyListActivity extends AppCompatActivity implements RecipeViewInterface {
    public Button search_button;
    public Button list_button;
    public EditText input_plainText;
    RecyclerView recycler_View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_buy_list);

        search_button=findViewById(R.id.search_button);
        list_button=findViewById(R.id.list_button);

        recycler_View=findViewById(R.id.recyclerView);

        input_plainText =findViewById(R.id.input_editText);

        ToBuyListAdapter toBuyListAdapter = new ToBuyListAdapter(this,this);
        recycler_View.setAdapter(toBuyListAdapter);
        recycler_View.setLayoutManager(new LinearLayoutManager(this));
        RecipeStorage.getRecipes();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent=new Intent(this, IngredientsActivity.class);
        intent.putExtra("id",RecipeStorage.getRecipe(position).id);
        startActivity(intent);
    }
}