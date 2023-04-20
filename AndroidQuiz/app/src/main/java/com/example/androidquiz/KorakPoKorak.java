package com.example.androidquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class KorakPoKorak extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_korak_po_korak);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Korak po korak");
    }
}