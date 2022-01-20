package com.example.sbdprojekt.ToBuyList;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.sbdprojekt.R;
import com.example.sbdprojekt.Recipe.RecepieListAdapter;
import com.example.sbdprojekt.Recipe.Recipe;
import com.example.sbdprojekt.Recipe.RecipeStorage;
import com.example.sbdprojekt.Recipe.RecipeViewInterface;
import com.example.sbdprojekt.RequestQueueSingleton;

import java.util.LinkedList;

public class ToBuyListAdapter extends RecyclerView.Adapter<ToBuyListAdapter.ToBuyListHolder> {
    private final RecipeViewInterface recipeViewInterface;
    int i=0;
    Context context;

    public ToBuyListAdapter(Context context,RecipeViewInterface recipeViewInterface) {
        this.context=context;
        this.recipeViewInterface = recipeViewInterface;
    }

    @NonNull
    @Override
    public ToBuyListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recepie_fragment, parent, false);
        return new ToBuyListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToBuyListHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return RecipeStorage.getRecipes().size();
    }

    public class ToBuyListHolder extends RecyclerView.ViewHolder{
        TextView recepieName;
        ImageView imageView;

        public ToBuyListHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            recepieName = itemView.findViewById(R.id.recipe_name);

            if (RecipeStorage.getRecipes().size()>0) {

                recepieName.setText(RecipeStorage.getRecipe(i).getName());
                RequestQueueSingleton.getInstance(itemView.getContext()).getImageLoader().get(
                        "https://spoonacular.com/recipeImages/"+ RecipeStorage.getRecipe(i).getImageApi(),
                        new ImageLoader.ImageListener() {
                            @Override
                            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                                imageView.setImageBitmap(response.getBitmap());
                            }

                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                RecipeStorage.getRecipe(i).setId(i);
                i++;

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ToBuyListAdapter.this.recipeViewInterface != null){
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION){
                                ToBuyListAdapter.this.recipeViewInterface.onItemClick(position);
                            }
                        }
                    }
                });

            }
        }
    }
}
