package com.example.sbdprojekt.toBuyList;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sbdprojekt.R;

public class ToBuyListActivity extends AppCompatActivity {

    String recepieNames[];
    String recepieDescriptions[];
    int images[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tobuylistview);
    }
}
