package com.example.sbdprojekt.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sbdprojekt.R;


public class IngredientsFragment extends Fragment {
    View view;
    TextView textView;
    String ingredient;

    public IngredientsFragment(int contentLayoutId, String ingredient) {
        super(contentLayoutId);
        this.ingredient = ingredient;
    }

    public IngredientsFragment() {
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putString("ingredient",ingredient);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        try {
            textView.setText(savedInstanceState.getString("ingredient"));
        }
        catch (NullPointerException e){}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ingredients, container, false);
        textView = view.findViewById(R.id.ingredients_textView);
        textView.setText(ingredient);
        return view;
    }
}