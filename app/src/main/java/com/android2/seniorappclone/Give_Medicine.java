package com.android2.seniorappclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Give_Medicine extends AppCompatActivity {

    private static final String TAG = "Data";

    //ini
    EditText searchView;



    GiveMedicineAdapter adapter;
    RecyclerView add_medicine_recycler_list;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference membersRef = db.collection("Members");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give__medicine);

        give_members_display();

        searchMember();



    }

    private void give_members_display() {

        //Query ng gusto mo maging display ng recycler view ung query ko yung mga nasa database na may field na status at value "active"

        Query query = membersRef.whereEqualTo("status", "Active");
        FirestoreRecyclerOptions<Members> options = new FirestoreRecyclerOptions.Builder<Members>()
                .setQuery(query, Members.class)
                .build();

        adapter = new GiveMedicineAdapter(options);

        add_medicine_recycler_list = findViewById(R.id.add_medicine_recycler_list);
        add_medicine_recycler_list.setHasFixedSize(true);
        add_medicine_recycler_list.setLayoutManager(new LinearLayoutManager(this));
        add_medicine_recycler_list.setAdapter(adapter);


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
        if (adapter != null) {
            adapter.stopListening();
        }
    }

    public void searchMember() {

        //Search
        searchView = findViewById(R.id.search_bar);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                Query query;

                Log.d(TAG, "Search box has changed to: " + s.toString());
                if (s.toString().isEmpty()) {

                    query = membersRef.whereEqualTo("status", "Active");
                    FirestoreRecyclerOptions<Members> options = new FirestoreRecyclerOptions.Builder<Members>()
                            .setQuery(query, Members.class)
                            .build();
                    adapter.updateOptions(options);


                } else {

                    query = membersRef.whereEqualTo("fullname", s.toString()).orderBy("timestamp", Query.Direction.DESCENDING);
                    FirestoreRecyclerOptions<Members> options = new FirestoreRecyclerOptions.Builder<Members>()
                            .setQuery(query, Members.class)
                            .build();

                    adapter.updateOptions(options);

                }

            }
        });


    }
}