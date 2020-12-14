package com.android2.seniorappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {


    ProgressBar progressBar2;
    TextInputLayout fullName, email, password, phone;
    Button RegisterBtn;
    FirebaseAuth fauth;
    FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //ini

        progressBar2 = findViewById(R.id.progressBar_circular);


        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();


        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.eMail);
        password = findViewById(R.id.passWord);
        phone = findViewById(R.id.phoneNumber);


        RegisterBtn = findViewById(R.id.btnRegister);
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //pinalita kong getEdittext
                String user_email = email.getEditText().getText().toString().trim();
                String user_password = password.getEditText().getText().toString().trim();

                if (TextUtils.isEmpty(user_email)) {
                    email.setError("Email is required.");
                    return;
                }

                if (TextUtils.isEmpty(user_password)) {
                    password.setError("Password is required.");
                    return;
                }

                if (user_password.length() < 6) {
                    password.setError("Password must be atleast 6 characters.");
                    return;
                }

                progressBar2.setVisibility(View.VISIBLE);

                fauth.createUserWithEmailAndPassword(user_email, user_password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        //Create a Collection of User in FireStore Database

                        FirebaseUser user = fauth.getCurrentUser();

                        DocumentReference df = fstore.collection("Users").document(user.getUid());
                        Map<String, Object> userInfo = new HashMap<>();
                        userInfo.put("FullName", fullName.getEditText().getText().toString());
                        userInfo.put("UserEmail", email.getEditText().getText().toString());
                        userInfo.put("UserPhone", phone.getEditText().getText().toString());

                        df.set(userInfo);
                        startActivity(new Intent(getApplicationContext(),LogInActivity.class));
                        finish();


                        Toast.makeText(Registration.this, "Account Created", Toast.LENGTH_SHORT).show();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(Registration.this, "Failed to create account", Toast.LENGTH_SHORT).show();
                        progressBar2.setVisibility(View.GONE);
                    }
                });


            }
        });


    }
    public void clickToLoginPage(View view){
        startActivity(new Intent(getApplicationContext(),LogInActivity.class));
    }


}