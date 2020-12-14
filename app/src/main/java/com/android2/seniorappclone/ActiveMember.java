package com.android2.seniorappclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ActiveMember extends AppCompatActivity {

    //ini


    MembersAdapter adapter;
    RecyclerView member_recycler_list;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference membersRef = db.collection("Members");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_member);

        active_members_display();


    }


    private void active_members_display() {

        //Query ng gusto mo maging display ng recycler view ung query ko yung mga nasa database na may field na status at value "active"

        Query query = membersRef.whereEqualTo("status", "Active");
        FirestoreRecyclerOptions<Members> options = new FirestoreRecyclerOptions.Builder<Members>()
                .setQuery(query, Members.class)
                .build();

        adapter = new MembersAdapter(options);

        member_recycler_list = findViewById(R.id.member_recycler_list);
        member_recycler_list.setHasFixedSize(true);
        member_recycler_list.setLayoutManager(new LinearLayoutManager(this));
        member_recycler_list.setAdapter(adapter);


    }

    //Kelangan ito para magstart magrun ang recyclerview
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
