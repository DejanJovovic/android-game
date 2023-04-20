package com.example.androidquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Skocko extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skocko);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Skocko");
    }
}