package com.example.androidquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignUpPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Sign up");
    }

    public void LogIn(View v) {
        Intent intent = new Intent(this, LogInPage.class);
        startActivity(intent);
    }
}