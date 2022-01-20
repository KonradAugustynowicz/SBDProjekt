package com.example.sbdprojekt.Recipe;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.sbdprojekt.R;
import com.example.sbdprojekt.RequestQueueSingleton;

import java.util.LinkedList;

public class RecepieListAdapter extends RecyclerView.Adapter<RecepieListAdapter.RecepieListHolder> {
    private final RecipeViewInterface recipeViewInterface;
    LinkedList<Recipe> recipeList=new LinkedList<>();

    Context context;
    int i=0;

    public RecepieListAdapter(Context context,
                              LinkedList<Recipe> recipeList,
                              RecipeViewInterface recipeViewInterface){
        this.context=context;
        this.recipeViewInterface=recipeViewInterface;
        this.notifyDataSetChanged();
        this.recipeList=recipeList;
    }

    @NonNull
    @Override
    public RecepieListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recepie_fragment,parent,false);
        return new RecepieListHolder(view,recipeViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecepieListHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class RecepieListHolder extends RecyclerView.ViewHolder{
        TextView recepieName;
        ImageView imageView;

        public RecepieListHolder(@NonNull View itemView, RecipeViewInterface recipeViewInterface) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            recepieName = itemView.findViewById(R.id.recipe_name);
            if (recipeList.size()>0) {
                recepieName.setText(recipeList.get(i).getName());
                RequestQueueSingleton.getInstance(itemView.getContext()).getImageLoader().get(
                        "https://spoonacular.com/recipeImages/"+ recipeList.get(i).getImageApi(),
                        new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        imageView.setImageBitmap(response.getBitmap());
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                i++;
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (RecepieListAdapter.this.recipeViewInterface != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            RecepieListAdapter.this.recipeViewInterface.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
