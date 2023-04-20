package com.example.androidquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

//    private Button btnSignUp, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("AndroidGame");

//        btnSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, SignUpPage.class);
//                startActivity(intent);
//            }
//        });
//
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, LogInPage.class);
//                startActivity(intent);
//            }
//        });

    }

    public void SignIn(View v) {
        Intent intent = new Intent(this, SignUpPage.class);
        startActivity(intent);
    }

    public void LogIn(View v) {
        Intent intent = new Intent(this, LogInPage.class);
        startActivity(intent);
    }

    public void Home(View v) {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }



}