package com.android2.seniorappclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class Medication extends AppCompatActivity {

    //Listener
    private static final String TAG = null;

    //Dialog
    Dialog dialog;
    Button cancel, Add_med;
    EditText med_des, med_note;
    String med_Description;
    String med_notes;

    //global
    String newId;
    String mName;
    String Member_Id;
    String ImageUri;


    TextView Display_Name_For_Medication;
    ImageView View_SeniorFace;
    FloatingActionButton add_medicine;


    //firebase FiresSore collection and document reference
    CollectionReference GiveMedicine;
    DocumentReference getMedRef;

    //fire Store
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    DocumentReference membersRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);


        //get intent
        newId = (getIntent().getStringExtra("id") + "");
        membersRef = fStore.collection("Members").document(newId);

        GiveMedicine = fStore.collection("GiveMedicine");
        getMedRef = fStore.collection("GiveMedicine").document();

        View_SeniorFace = findViewById(R.id.imageView_holder_med);
        Display_Name_For_Medication = findViewById(R.id.Edit_Profile_Page_Name);


        //add medicine
        add_medicine = findViewById(R.id.btn_add_medicine);
        add_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.show();
            }
        });

        //Dialog

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.medicine_layout_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        //cancel button dialog
        cancel = dialog.findViewById(R.id.BTN_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Add_med = dialog.findViewById(R.id.BTN_ok);
        Add_med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                med_des = dialog.findViewById(R.id.put_med_description);
                med_Description = med_des.getText().toString();

                med_note = dialog.findViewById(R.id.put_med_notes);
                med_notes = med_note.getText().toString();

                if (validMedDes() && validMedNote()) {

                    GiveMedicinItem gi = new GiveMedicinItem();

                    gi.setFullName(mName);
                    gi.setMemberMedicineDescription(med_Description);
                    gi.setMemberMedicineNote(med_notes);
                    gi.setMember_id(Member_Id);

                    GiveMedicine.add(gi);

                    Snackbar.make(findViewById(android.R.id.content), "added", Snackbar.LENGTH_LONG).show();


                }


            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();


        membersRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {


                //Handle Error Pag may problema sa pag picture si addSnapshotListener sa membersRef na reference natin para sa document sa ating database
                if (error != null) {
                    Toast.makeText(Medication.this, "Error while loading!", Toast.LENGTH_LONG).show();
                    Log.d(TAG, error.toString());
                    return;
                }
                if (value.exists()) {

                    mName = value.getString("fullname");
                    Member_Id = value.getString("member_id");
                    Display_Name_For_Medication.setText(mName);
                    ImageUri = value.getString("member_ImageUri");
                    Picasso.get().load(ImageUri).into(View_SeniorFace);


                }


            }
        });


    }

    public boolean validMedDes() {

        String MDes = med_des.getText().toString();

        if (MDes.isEmpty()) {
            med_des.setError("Field cannot be empty");
            return false;
        } else {
            med_des.setError(null);
            return true;
        }

    }

    public boolean validMedNote() {

        String MNotes = med_note.getText().toString();

        if (MNotes.isEmpty()) {
            med_note.setError("Field cannot be empty");
            return false;
        } else {
            med_note.setError(null);
            return true;
        }

    }


}