package com.android2.seniorappclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class InActiveMember extends AppCompatActivity {


    MembersAdapter adapter;
    RecyclerView member_recycler_list;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference membersRef = db.collection("Members");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_active_member);

        //display tinawag na dito sa loob ng onCreate yung recyclerview para mag display na siya
        active_members_display();


    }

    private void active_members_display() {

        //Query ng gusto mo maging display ng recycler view ung query ko yung mga nasa database na may field na status at value "not active"

        Query query = membersRef.whereEqualTo("status","Not Active");
        FirestoreRecyclerOptions<Members> options = new FirestoreRecyclerOptions.Builder<Members>()
                .setQuery(query, Members.class)
                .build();

        adapter = new MembersAdapter(options);

        member_recycler_list = findViewById(R.id.notActive_member_recycler_list);
        member_recycler_list.setHasFixedSize(true);
        member_recycler_list.setLayoutManager(new LinearLayoutManager(this));
        member_recycler_list.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}