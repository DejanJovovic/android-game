package com.example.androidquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MojBroj extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moj_broj);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Moj broj");
    }
}