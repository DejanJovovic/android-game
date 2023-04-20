package com.example.androidquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Asosijacije extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asosijacije);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Asosijacije");
    }
}