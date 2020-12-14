package com.android2.seniorappclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {


    //firebase

    FirebaseAuth fAuth;
    FirebaseUser currentUser;

    CardView card_add_member;
    CardView card_master_list;
    CardView card_event_meeting;
    CardView card_contribution;
    TextView display_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activity);

        //ini fire base
        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();

        //display username sa tass
        display_username = findViewById(R.id.DisplayUsername);
        display_username.setText(currentUser.getEmail());


        //ngaun
        //ini card_profile to click
        card_add_member = findViewById(R.id.card_profile);
        card_add_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //to profile module

                startActivity(new Intent(getApplicationContext(),AddMember.class));

                Toast.makeText(HomeActivity.this, "Magdagdag ng kasapi", Toast.LENGTH_LONG).show();


            }
        });

        //ini card_master_list
        card_master_list = findViewById(R.id.card_members);
        card_master_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to masters List module

                startActivity(new Intent(getApplicationContext(),chooseInMasterlist.class));


            }
        });


        //ini card event meeting
        card_event_meeting = findViewById(R.id.card_meeting);
        card_event_meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to event
                Toast.makeText(HomeActivity.this, "Alisin na lang Pag di nagawa", Toast.LENGTH_LONG).show();
            }
        });

        //ini card contributions
        card_contribution = findViewById(R.id.card_contribution);
        card_contribution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(HomeActivity.this, "Alisin na Din Pag di nagawa", Toast.LENGTH_LONG).show();

            }
        });

    }

    //sign-out

    public void btn_sign_out(View view) {


        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LogInActivity.class));
        finish();


    }
}