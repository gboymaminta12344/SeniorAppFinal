package com.android2.seniorappclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ListOfGiven extends AppCompatActivity {

    private static final String TAG = "Data";
    //ini
    EditText searchView2;

    //ini firebase
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference givenRef = db.collection("GiveMedicine");


    //ini
    RemarksAdapter adapter;
    RecyclerView given_medicine_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_given);


        //recycler view
        give_med_list();

        //search view
        searchGivenMed();

    }

    private void give_med_list() {

        //Query ng gusto mo maging display ng recycler view ung query ko yung mga nasa database na may field na status at value "active"

        Query query = givenRef.orderBy("member_id");
        FirestoreRecyclerOptions<GiveMedicinItem> options = new FirestoreRecyclerOptions.Builder<GiveMedicinItem>()
                .setQuery(query, GiveMedicinItem.class)
                .build();


        adapter = new RemarksAdapter(options);


        given_medicine_list = findViewById(R.id.add_medicine_recycler_list2);
        given_medicine_list.setHasFixedSize(true);
        given_medicine_list.setLayoutManager(new LinearLayoutManager(this));
        given_medicine_list.setAdapter(adapter);


    }

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


    public void searchGivenMed() {

        //Search
        searchView2 = findViewById(R.id.search_bar2);
        searchView2.addTextChangedListener(new TextWatcher() {
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

                    query = givenRef.orderBy("member_id");
                    FirestoreRecyclerOptions<GiveMedicinItem> options = new FirestoreRecyclerOptions.Builder<GiveMedicinItem>()
                            .setQuery(query, GiveMedicinItem.class)
                            .build();
                    adapter.updateOptions(options);


                } else {

                    query = givenRef.whereEqualTo("fullName", s.toString()).orderBy("timestamp", Query.Direction.DESCENDING);
                    FirestoreRecyclerOptions<GiveMedicinItem> options = new FirestoreRecyclerOptions.Builder<GiveMedicinItem>()
                            .setQuery(query, GiveMedicinItem.class)
                            .build();

                    adapter.updateOptions(options);

                }

            }
        });


    }
}