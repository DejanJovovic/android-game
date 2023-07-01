package com.example.androidquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInPage extends AppCompatActivity {


    EditText inputEmail, inputPassword;
    AppCompatButton login;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Log in");

        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        login = findViewById(R.id.loginbtn);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();
            }
        });

    }

    private void performLogin() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        if (!email.matches(emailPattern)) {
            inputEmail.setError("Enter Correct Email");

        } else if (password.isEmpty() || password.length() < 6) {
            inputPassword.setError("Password does not match the requirements");
        } else {
            progressDialog.setMessage("Please wait...");
            progressDialog.setTitle("Log In");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        sendUserToHomeActivity();
                        progressDialog.dismiss();
                        Toast.makeText(LogInPage.this, "Log In Successful", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(LogInPage.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }


    private void sendUserToHomeActivity() {
        Intent intent = new Intent(LogInPage.this, HomePage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public void SignIn(View v) {
        Intent intent = new Intent(this, SignUpPage.class);
        startActivity(intent);
    }


}