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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
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

public class UpdateMyProfile extends AppCompatActivity {

    //Listener
    private static final String TAG = null;

    String newId;
    String FName;
    String UPhone;
    String UPosition;

    //fire Store
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    DocumentReference usersRef;

    //fire storage
    FirebaseStorage storage;
    StorageReference storageReference;
    StorageTask uploadImageTask;


    //ini
    ImageView update_user_image;
    EditText update_user_FName;
    EditText update_user_Phone;
    EditText update_user_position;
    Button update_picture_user;

    //lumang Image uri pag di pa na update ang pic
    String user_ImageUri;

    //new uri
    Uri updated_ImageUri;

    //global par
    String downloadUrl;


    //Calling Property Class
    Users users = new Users();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_my_profile);


        //get intent
        newId = (getIntent().getStringExtra("id") + "");
        usersRef = fStore.collection("Users").document(newId);


        //findViews
        update_user_FName = findViewById(R.id.Edit_Profile_Page_Name2_profile);
        update_user_Phone = findViewById(R.id.Edit_Profile_Page_Contact_profile);
        update_user_position = findViewById(R.id.Edit_Profile_Page_position_profile);


        //firebase storage open this open this comment
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("Senior App Member");

        //Display image dito sa oncreate View
        //pag sa on start kasi nilagay yung image hindi mapapaplitan
        displayImageOnCreate();


        //imageView set on click
        update_user_image = findViewById(R.id.imageView_holder2_profile);
        update_user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //put picture on ImageView
                choosePicture();


            }
        });

        update_picture_user = findViewById(R.id.BTN_update_PhotoUpdate_profile);
        update_picture_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //update Image
                if (uploadImageTask != null && uploadImageTask.isInProgress()) {

                    Snackbar.make(findViewById(android.R.id.content), "In Progress", Snackbar.LENGTH_LONG).show();

                } else {
                    //update image
                    updateImage();

                }

                Toast.makeText(UpdateMyProfile.this, "click", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(UpdateMyProfile.this, "Error while loading!", Toast.LENGTH_LONG).show();
                    Log.d(TAG, error.toString());
                    return;
                }

                //Condition nung na picturan ni addSnapshotListener yung loob ng mga documents at mga fields na sa documents natin na member
                if (value.exists()) {


                    FName = value.getString("fullName");
                    UPhone = value.getString("userPhone");
                    UPosition = value.getString("userPosition");


                    update_user_FName.setText(FName);
                    update_user_Phone.setText(UPhone);
                    update_user_position.setText(UPosition);


                }

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
            Picasso.get().load(updated_ImageUri).into(update_user_image);

            //enable the button if intent is trigger

            update_picture_user.setEnabled(true);

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


                            users.setUser_ImageUri(downloadUrl);
                            usersRef.set(users, SetOptions.mergeFields("user_ImageUri"));


                            Snackbar.make(findViewById(android.R.id.content), "Uploaded", Snackbar.LENGTH_LONG).show();

                            update_picture_user.setEnabled(false);
                            update_user_image.setEnabled(true);


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
                    update_user_image.setEnabled(true);


                }
                //pag on progress
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                    //while on progress
                    double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    pd.setMessage("Percentage: " + (int) progressPercent + "%");
                    update_picture_user.setEnabled(false);
                    update_user_image.setEnabled(false);

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

        usersRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    Toast.makeText(UpdateMyProfile.this, "Error while loading!", Toast.LENGTH_LONG).show();
                    Log.d(TAG, error.toString());
                    return;
                }
                if (value.exists()) {

                    user_ImageUri = value.getString("user_ImageUri");
                    Picasso.get().load(user_ImageUri).into(update_user_image);


                }


            }
        });


    }
}