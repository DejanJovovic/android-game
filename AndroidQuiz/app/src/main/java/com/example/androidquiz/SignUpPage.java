package com.example.androidquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpPage extends AppCompatActivity {


    EditText inputEmail, inputUsername, inputPassword, inputConfirmPassword;
    AppCompatButton register;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    private FirebaseDatabase firebaseDatabase;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Sign up");


        inputEmail = findViewById(R.id.email);
        inputUsername = findViewById(R.id.username);
        inputPassword = findViewById(R.id.password);
        inputConfirmPassword = findViewById(R.id.repassword);
        register = findViewById(R.id.signupbtn);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getUsername = inputUsername.getText().toString();
                String getEmail = inputEmail.getText().toString();
                String getPassword = inputPassword.getText().toString();
                String getUid = mAuth.getUid();

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("username", getUsername);
                hashMap.put("email", getEmail);
                hashMap.put("password", getPassword);

                databaseReference.child("Users").child(getUid)
                                .setValue(hashMap);


                PerformAuth();
            }
        });


    }

    private void PerformAuth(){
        String email = inputEmail.getText().toString();
        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();
        String confrimPassword = inputConfirmPassword.getText().toString();


        if (!email.matches(emailPattern)) {
            inputEmail.setError("Enter Correct Email");

        } else if(username.isEmpty() || username.length()<3) {
            inputUsername.setError("Username does not match the requirements");
        }
        else if(password.isEmpty()|| password.length()<6) {
            inputPassword.setError("Password does not match the requirements");
        }
        else if(!password.equals(confrimPassword)) {
            inputConfirmPassword.setError("Password does not match");
        } else {
            progressDialog.setMessage("Please wait...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        sendUserToLogIn();
                        progressDialog.dismiss();
                        Toast.makeText(SignUpPage.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(SignUpPage.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void sendUserToLogIn() {
       Intent intent = new Intent(SignUpPage.this, LogInPage.class);
       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
       startActivity(intent);
    }
    public void LogIn(View v) {
        Intent intent = new Intent(this, LogInPage.class);
        startActivity(intent);
    }
}