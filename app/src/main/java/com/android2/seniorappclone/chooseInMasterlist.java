package com.android2.seniorappclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class chooseInMasterlist extends AppCompatActivity {

    private CardView cardViewActive;
    private CardView cardViewNotActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_in_masterlist);


        //ini

        cardViewActive = findViewById(R.id.card_active_members);
        cardViewActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // to active members recycler view
                startActivity(new Intent(getApplicationContext(),ActiveMember.class));



            }
        });

        cardViewNotActive = findViewById(R.id.card_Deceased_members);
        cardViewNotActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),InActiveMember.class));

            }
        });
    }
}