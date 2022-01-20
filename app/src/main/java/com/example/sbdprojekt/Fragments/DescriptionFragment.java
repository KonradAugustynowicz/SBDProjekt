package com.example.sbdprojekt.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sbdprojekt.R;


public class DescriptionFragment extends Fragment {
    View view;
    TextView textView;
    String description;

    public DescriptionFragment(int contentLayoutId, String description) {
        super(contentLayoutId);
        this.description = description;
    }

    public DescriptionFragment() {
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("description",description);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        try {
            textView.setText(Html.fromHtml(description));
        }catch (NullPointerException e){

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_description, container, false);
        textView = view.findViewById(R.id.description_textView);
        textView.setText(Html.fromHtml(description!=null ? description : ""));
        return view;
    }
}