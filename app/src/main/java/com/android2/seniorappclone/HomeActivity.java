package com.android2.seniorappclone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {


    //Listener
    private static final String TAG = null;

    //firebase

    FirebaseAuth fAuth;
    FirebaseUser currentUser;

    //fire Store
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    DocumentReference usersRef;

    //fire storage
    FirebaseStorage storage;
    StorageReference storageReference;


    //variables to fetch the current data
    String CurrentLogin;
    String fname;
    String uphone;
    String user_ImageUri;


    CardView card_add_member;
    CardView card_master_list;
    CardView card_event_meeting;
    CardView card_contribution;
    TextView display_username;
    TextView my_profile_update;
    ImageView user_image_display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activity);


        //ini fire base
        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();

        CurrentLogin = currentUser.getUid();

        usersRef = fStore.collection("Users").document(CurrentLogin);


        //my profile update
        my_profile_update = findViewById(R.id.textViewEditProfile);
        my_profile_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = CurrentLogin;
                Intent intent = new Intent(v.getContext(), UpdateMyProfile.class);
                intent.putExtra("id", id);
                v.getContext().startActivity(intent);

            }
        });


        //ngaun
        //ini card_profile to click
        card_add_member = findViewById(R.id.card_profile);
        card_add_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //to profile module

                startActivity(new Intent(getApplicationContext(), AddMember.class));

                Toast.makeText(HomeActivity.this, "Magdagdag ng kasapi", Toast.LENGTH_LONG).show();


            }
        });

        //ini card_master_list
        card_master_list = findViewById(R.id.card_members);
        card_master_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to masters List module

                startActivity(new Intent(getApplicationContext(), chooseInMasterlist.class));


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

                startActivity(new Intent(getApplicationContext(), Give_Medicine.class));


            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        usersRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                //Handle Error Pag may problema sa pag picture si addSnapshotListener sa membersRef na reference natin para sa document sa ating database
                if (error != null) {
                    Toast.makeText(HomeActivity.this, "Error while loading!", Toast.LENGTH_LONG).show();
                    Log.d(TAG, error.toString());
                    return;
                }
                //Condition nung na picturan ni addSnapshotListener yung loob ng mga documents at mga fields na sa documents natin na member
                if (value.exists()) {


                    fname = value.getString("fullName");
                    uphone = value.getString("userPhone");
                    user_ImageUri = value.getString("user_ImageUri");

                    //display username sa tass
                    display_username = findViewById(R.id.DisplayUsername);
                    display_username.setText(fname);

                    user_image_display = findViewById(R.id.image_profile_pic);
                    Picasso.get().load(user_ImageUri).into(user_image_display);


                }

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