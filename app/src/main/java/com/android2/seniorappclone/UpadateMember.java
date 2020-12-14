package com.android2.seniorappclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class UpadateMember extends AppCompatActivity {


    //Listener
    private static final String TAG = null;

    //variables to fetch the current data nilagay natin dito para global magamit natin kahit saan dito sa mga gagawin natin na metod sa class na ito

    String newId;
    String mName;
    String mBday;
    String mGender;
    String mMunicipality;
    String mBrgy;
    String mZone;

    //lumang Image uri pag di pa na update ang pic
    String member_ImageUri;


    //status ng member
    String member_Status;

    //global par
    String downloadUrl;


    //radio button option
    Button update;
    Button update_picture;
    RadioGroup radioGroup;
    RadioButton radioButton;

    //fire Store
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    DocumentReference membersRef;


    //fire storage
    FirebaseStorage storage;
    StorageReference storageReference;
    StorageTask uploadImageTask;

    //ini
    ImageView update_member_image;
    EditText update_member_FName;
    EditText update_member_BDay;
    EditText update_member_Gender;
    EditText update_member_Municipality;
    EditText update_member_BRgy;
    EditText update_member_mZone;

    //new uri
    Uri updated_ImageUri;


    //Calling Property Class
    Members members = new Members();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upadate_member);

        //get intent
        newId = (getIntent().getStringExtra("id") + "");
        membersRef = fStore.collection("Members").document(newId);


        //findViews
        update_member_FName = findViewById(R.id.Edit_Profile_Page_Name2);
        update_member_BDay = findViewById(R.id.Edit_Profile_Page_Bday2);
        update_member_Gender = findViewById(R.id.Edit_Profile_Page_Gender2);
        update_member_Municipality = findViewById(R.id.Edit_Profile_Page_Municipality2);
        update_member_BRgy = findViewById(R.id.Edit_Profile_Page_Brgy2);
        update_member_mZone = findViewById(R.id.Edit_Profile_Page_Zone2);

        //firebase storage open this open this comment
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("Senior App Member");


        //Display image dito sa oncreate View
        //pag sa on start kasi nilagay yung image hindi mapapaplitan
        displayImageOnCreate();


        //imageView set on click
        update_member_image = findViewById(R.id.imageView_holder2);
        update_member_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //put picture on ImageView
                choosePicture();
            }
        });
        //update Image set on click
        update_picture = findViewById(R.id.BTN_update_PhotoUpdate);
        update_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update Image
                if (uploadImageTask != null && uploadImageTask.isInProgress()) {

                    Snackbar.make(findViewById(android.R.id.content), "In Progress", Snackbar.LENGTH_LONG).show();

                } else {
                    //update image
                    updateImage();

                }


            }
        });

        //Radio Button group set on click
        radioGroup = findViewById(R.id.state_status);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    //radio button active text

                    case R.id.active:
                        member_Status = "Active";
                        break;

                    //radio button inactive text
                    case R.id.Inactive:
                        member_Status = "Not Active";
                        break;

                }
            }
        });


        //click button update
        update = findViewById(R.id.Button_Profile_Page2);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //call the update method here

                upDateData();


            }
        });


    }

    //need ng onStart / dito ifetch yung data kesa sa on create sa taas

    @Override
    protected void onStart() {
        super.onStart();

        // method to load property info//activity:this automatic end the listening if close
        membersRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override


            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                //Handle Error Pag may problema sa pag picture si addSnapshotListener sa membersRef na reference natin para sa document sa ating database
                if (error != null) {
                    Toast.makeText(UpadateMember.this, "Error while loading!", Toast.LENGTH_LONG).show();
                    Log.d(TAG, error.toString());
                    return;
                }
                //Condition nung na picturan ni addSnapshotListener yung loob ng mga documents at mga fields na sa documents natin na member
                if (value.exists()) {

                    //pinicturan ni addSnapshotListener tapos ilagay niya yung laman sa mga String Variable na diniclare natin global ung sa taas
                    mName = value.getString("fullname");
                    mBday = value.getString("birthday");
                    mGender = value.getString("gender");
                    mMunicipality = value.getString("municipality");
                    member_Status = value.getString("status");
                    mBrgy = value.getString("barangay");
                    mZone = value.getString("zone");


                    //nag exist ba yung field sa database document?lagay natin sa Edit Text yung laman kung nag exist nga
                    update_member_FName.setText(mName);
                    update_member_BDay.setText(mBday);
                    update_member_Gender.setText(mGender);
                    update_member_Municipality.setText(mMunicipality);
                    update_member_BRgy.setText(mBrgy);
                    update_member_mZone.setText(mZone);

                    //end of if else para pag open auto toggle ung button depende sa status ng member
                    if (member_Status.equals("Active")) {

                        RadioButton rb1 = findViewById(R.id.active);
                        rb1.setChecked(true);
                    } else {

                        RadioButton rb2 = findViewById(R.id.Inactive);
                        rb2.setChecked(true);

                    }
                    //lalabas ito pag pinindot ung update sasabihin niya status ng member
                    Toast.makeText(UpadateMember.this, "This Member is:   " + member_Status, Toast.LENGTH_LONG).show();
                    // end ng if else condition

                }

            }
        });

    }

    //update data
    private void upDateData() {

        String updatedName = update_member_FName.getText().toString();
        String updateBDate = update_member_BDay.getText().toString();
        String updateGender = update_member_Gender.getText().toString();
        String updateMunicipality = update_member_Municipality.getText().toString();
        String updateBRgy = update_member_BRgy.getText().toString();
        String updateZone = update_member_mZone.getText().toString();

        //ung nas members na class ung may getter and setter
        members.setStatus(member_Status);
        members.setFullname(updatedName);
        members.setBirthday(updateBDate);
        members.setGender(updateGender);
        members.setMunicipality(updateMunicipality);
        members.setBarangay(updateBRgy);
        members.setZone(updateZone);

        //progress bar
        final ProgressDialog pd2 = new ProgressDialog(this);
        pd2.setTitle("Updating Data");
        pd2.show();

        //membersRef.set()<--query ng fire store sa pag update ng data
        membersRef.set(members, SetOptions.mergeFields("fullname", "status", "birthday", "gender", "municipality", "barangay", "zone")).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Snackbar.make(findViewById(android.R.id.content), "UpDated", Snackbar.LENGTH_LONG).show();
                pd2.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


                Snackbar.make(findViewById(android.R.id.content), "Failed to update", Snackbar.LENGTH_LONG).show();
                pd2.dismiss();
            }
        });





    }


    //choose picture from the gallery

    //still not finish
    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            updated_ImageUri = data.getData();
            Picasso.get().load(updated_ImageUri).into(update_member_image);

            //enable the button if intent is trigger
            update_picture.setEnabled(true);
        }

    }

    //update the image
    private void updateImage() {

        //progress bar
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Updating Image");
        pd.show();

        if (updated_ImageUri != null) {

            //Data to Storage
            StorageReference fileReference = storageReference.child("Senior App Member/" + UUID.randomUUID().toString() + "." + getFileExtension(updated_ImageUri));
            uploadImageTask = fileReference.putFile(updated_ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                //pag success
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            //dismiss progress dialog if updated
                            pd.dismiss();

                            downloadUrl = uri.toString();

                            members.setMember_ImageUri(downloadUrl);
                            membersRef.set(members, SetOptions.mergeFields("member_ImageUri"));


                            Snackbar.make(findViewById(android.R.id.content), "Uploaded", Snackbar.LENGTH_LONG).show();

                            update_picture.setEnabled(false);
                            update.setEnabled(true);
                            update_member_image.setEnabled(true);


                        }
                    });

                }
                //pag failed
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    //handle error
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(), "Unable to Update", Toast.LENGTH_LONG).show();
                    update_member_image.setEnabled(true);
                    update.setEnabled(true);


                }
                //pag on progress
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                    //while on progress
                    double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    pd.setMessage("Percentage: " + (int) progressPercent + "%");
                    update_picture.setEnabled(false);
                    update.setEnabled(false);
                    update_member_image.setEnabled(false);

                }
            });


        }
    }

    //image insert to data storage
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    //display image on the onCreate tinawag na sa on create sa taas

    private void displayImageOnCreate() {

        membersRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    Toast.makeText(UpadateMember.this, "Error while loading!", Toast.LENGTH_LONG).show();
                    Log.d(TAG, error.toString());
                    return;
                }
                if (value.exists()) {

                    member_ImageUri = value.getString("member_ImageUri");
                    Picasso.get().load(member_ImageUri).into(update_member_image);


                }


            }
        });


    }
}