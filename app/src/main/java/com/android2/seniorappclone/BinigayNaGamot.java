package com.android2.seniorappclone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ServerTimestamp;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class BinigayNaGamot extends AppCompatActivity {

    //Listener
    private static final String TAG = null;

    ImageView SFace;
    TextView SeniorNameInfo;
    TextView SeniorMedDate;
    TextView SeniorMedNameGiven;
    TextView SeniorMedNote;
    TextView SeniorGivenBy;
    TextView SeniorWhoGavePosition;

    //global
    String newId;
    String given_date;

    String SName;
    String ImageUri;
    String MedNameGiven;
    String MedNoteGiven;
    String MedGivenBy;
    String Position;

    //fire Store
    //fire Store
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    DocumentReference givenMedRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binigay_na_gamot);

        //get intent
        newId = (getIntent().getStringExtra("id") + "");
        given_date = (getIntent().getStringExtra("given_date") + "");
        givenMedRef = fStore.collection("GiveMedicine").document(newId);


    }

    @Override
    protected void onStart() {
        super.onStart();

        //ini
        SFace = findViewById(R.id.imageView_holder_med_binigay);
        SeniorNameInfo = findViewById(R.id.Edit_Profile_Page_Name_information);
        SeniorMedDate = findViewById(R.id.text_display_date_information);
        SeniorMedNameGiven = findViewById(R.id.text_display_medicName_information);
        SeniorMedNote = findViewById(R.id.text_display_medicNote_information);
        SeniorGivenBy = findViewById(R.id.text_display_given_by_information);
        SeniorWhoGavePosition = findViewById(R.id.text_display_position_information);


        givenMedRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    Toast.makeText(BinigayNaGamot.this, "Error while loading!", Toast.LENGTH_LONG).show();
                    Log.d(TAG, error.toString());
                    return;
                }

                if (value.exists()) {

                    SName = value.getString("fullName");
                    ImageUri = value.getString("image_of_sen");
                    MedNameGiven = value.getString("memberMedicineDescription");
                    MedNoteGiven = value.getString("memberMedicineNote");
                    MedGivenBy = value.getString("given_by");
                    Position = value.getString("given_by_position");

                    SeniorWhoGavePosition.setText(Position);
                    SeniorGivenBy.setText(MedGivenBy);
                    SeniorMedNote.setText(MedNoteGiven);
                    SeniorMedNameGiven.setText(MedNameGiven);
                    SeniorMedDate.setText(given_date);
                    SeniorNameInfo.setText(SName);
                    Picasso.get().load(ImageUri).into(SFace);


                }

            }
        });


    }
}