package com.example.androidquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        setTitle("Home");
    }

    public void profile(View v) {
        Intent intent = new Intent(this, UserProfile.class);
        startActivity(intent);
    }

    public void games(View v) {
        Intent intent = new Intent(this, Games.class);
        startActivity(intent);
    }

    public void signOff(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}