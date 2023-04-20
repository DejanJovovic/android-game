package com.example.androidquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class KoZnaZna extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ko_zna_zna);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Ko zna zna");
    }
}