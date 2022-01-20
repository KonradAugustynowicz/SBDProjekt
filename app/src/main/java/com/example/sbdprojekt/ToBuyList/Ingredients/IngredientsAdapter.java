package com.example.sbdprojekt.ToBuyList.Ingredients;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sbdprojekt.R;
import com.example.sbdprojekt.Recipe.RecipeStorage;

import java.util.LinkedList;
import java.util.zip.Inflater;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsHolder> {
    Context context;
    int recipeId;

    public IngredientsAdapter(Context context,int recipeId) {
        this.context=context;
        this.recipeId=recipeId;
    }

    @NonNull
    @Override
    public IngredientsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ingredient,parent,false);
        return new IngredientsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.ingredient.setText(IngredientsStorage.getIngredients().get(position).getName());
        holder.checkBox.setChecked(IngredientsStorage.getIngredients().get(position).isChecked());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                IngredientsStorage.getIngredients().get(position).setChecked(b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return RecipeStorage.getRecipe(recipeId).getIngredients().size();
    }

    public class IngredientsHolder extends RecyclerView.ViewHolder{
        TextView ingredient;
        CheckBox checkBox;

        public IngredientsHolder(@NonNull View itemView) {
            super(itemView);
            ingredient = itemView.findViewById(R.id.ingredients_textView);
            checkBox = itemView.findViewById(R.id.checkBox);


        }
    }
}
