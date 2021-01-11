package com.android2.seniorappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LogInActivity extends AppCompatActivity {

    Button ToReg;
    Button LogIn;
    TextInputLayout username, userpassword;
    FirebaseAuth fauth;
    FirebaseFirestore fstore;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //ini
        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        //keep me log in
        if (fauth.getCurrentUser() != null) {

            DocumentReference df = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    if (documentSnapshot.getString("userEmail") != null) {

                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        finish();


                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(), LogInActivity.class));
                    finish();

                }
            });


        }


        //ini
        progressBar = findViewById(R.id.progressBar);
        username = findViewById(R.id.eMail);
        userpassword = findViewById(R.id.passWord);

        LogIn = findViewById(R.id.btnLogin);

        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validUserName() && validPassWord()) {

                    progressBar.setVisibility(View.VISIBLE);
                    fauth.signInWithEmailAndPassword(username.getEditText().getText().toString(), userpassword.getEditText().getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            checkUserAccessLevel(authResult.getUser().getUid());
                            progressBar.setVisibility(View.GONE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(LogInActivity.this, "Record not found please register", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });

                }


            }


        });


        //Button to Registration Page
        ToReg = findViewById(R.id.gotoRegister);
        ToReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(LogInActivity.this, "click", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Registration.class));
            }
        });


    }

    //intent where log in or not and check username inside the database
    private void checkUserAccessLevel(String uid) {
        DocumentReference df = fstore.collection("Users").document(uid);
        //extract data from the document
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                Log.d("TAG", "onSuccess" + documentSnapshot.getData());


                if (documentSnapshot.getString("userEmail") != null) {

                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                }
            }
        });
    }

    public boolean validUserName() {
        String UName = username.getEditText().getText().toString();
        if (UName.isEmpty()) {
            username.setError("Field cannot be empty");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }

    }

    public boolean validPassWord() {

        String UserPWord = userpassword.getEditText().getText().toString();
        if (UserPWord.isEmpty()) {
            userpassword.setError("Field cannot be empty");
            return false;

        } else {

            userpassword.setError(null);
            userpassword.setErrorEnabled(false);
            return true;
        }

    }


}

