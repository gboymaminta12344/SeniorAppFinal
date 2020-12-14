package com.android2.seniorappclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class AddMember extends AppCompatActivity {

    //firebase
    FirebaseAuth fAuth;
    FirebaseUser currentUser;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();


    //firebase storage reference
    private FirebaseStorage storage;
    private StorageReference storageReference;

    //Nilagay Sa Taas  Para maging Global para ma call sa lahat ng part ng program
    StorageTask uploadImageTask;

    //firebase FiresSore collection and document reference
    DocumentReference membersRef;
    CollectionReference listOfMembers;

    //
    public static final String MEMBER = "member";
    private ImageView imageView;
    private TextView nameView, birthdayView, genderView, municipalityView, barangayView, zoneView;
    private Button add_Member;
    public Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);



        //firebase storage open getting instance inside "the onCreate(Bundle savedInstanceState)"
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("Senior App Member");

        //fire Store initialization
        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();

        //firebase fireStore Database  open getting instance inside "the onCreate(Bundle savedInstanceState)"
        listOfMembers = fStore.collection("Members");
        membersRef = fStore.collection("Members").document();


        //ini
        imageView = findViewById(R.id.imageView_holder);
        nameView = findViewById(R.id.Edit_Profile_Page_Name);
        birthdayView = findViewById(R.id.Edit_Profile_Page_Bday);
        genderView = findViewById(R.id.Edit_Profile_Page_Gender);
        municipalityView = findViewById(R.id.Edit_Profile_Page_Municipality);
        barangayView = findViewById(R.id.Edit_Profile_Page_Brgy);
        zoneView = findViewById(R.id.Edit_Profile_Page_Zone);

        //Button for adding member
        add_Member = findViewById(R.id.Button_Profile_Page);
        add_Member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //condition habang nasa process ng pagpa process addMemberImage();
                if (uploadImageTask != null && uploadImageTask.isInProgress()) {


                    Snackbar.make(findViewById(android.R.id.content), "In Progress", Snackbar.LENGTH_LONG).show();


                } else {

                    if (imageUri == null) {
                        Snackbar.make(findViewById(android.R.id.content), "choose picture", Snackbar.LENGTH_LONG).show();
                    } else {

                        //call the adding of image at the bottom
                        addMemberImage();


                    }


                }


            }


        });

        //add image when image view is click
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //call the adding of picture at the bottom
                choosePicture();
            }
        });

    }

    //method to choose a picture and put on the imageview

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
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(imageView);

        }

    }

    //method getting a file extension of image
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));

    }
    //adding member image

     //put the process in a separate method to call it later in button click "add_Member.setOnclickListener" na Button
    private void addMemberImage() {

        //progress dialog
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Adding Member");
        pd.show();


        //condition para mag add / imageUri got the imageUri
        if (imageUri != null) {

            //start adding

            //1.get reference of your storage "in this case it will be save on a members images folder with the extension file declared"

            StorageReference fileReference = storageReference.child("Members Images" + UUID.randomUUID().toString() + "." + getFileExtension(imageUri));

            //2. put the image uri or image to the storage or storage reference we created at number 1
            uploadImageTask = fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                //3. if uploading the image uri or image on our storage get a downloaded uri or image reference that can be use later for fetching the uploaded image
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //4. trough the object fileReference we get the downloaded uri of image that can be use for fetching the image later or updating the image
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        //5.got the image uri now (Uri uri)
                        public void onSuccess(Uri uri) {

                            pd.dismiss(); //dismiss the progress dialog so it wont run we the process is success

                            //6.converting the image uri to a string and put it on a String variable "downloadUrl"
                            String downloadUrl = uri.toString();
                            //7.other data we need for our member

                            //Process sa pag add nilagay lang sa loob din ng pagupload para isang click na lang ng button
                            String memberName = nameView.getText().toString();
                            String memberBDate = birthdayView.getText().toString();
                            String memberGender = genderView.getText().toString();
                            String memberMunicipality = municipalityView.getText().toString();
                            String memberBRgy = barangayView.getText().toString();
                            String memberZone = zoneView.getText().toString();


                           //Custom object na member tinawag dito para magamit
                            Members mb = new Members();

                            //mga laman ng custom object na Members
                            mb.setStatus("Active");
                            mb.setMember_id(membersRef.getId());
                            mb.setAddedBy(currentUser.getEmail());
                            mb.setFullname(memberName);
                            mb.setBirthday(memberBDate);
                            mb.setGender(memberGender);
                            mb.setMunicipality(memberMunicipality);
                            mb.setBarangay(memberBRgy);
                            mb.setZone(memberZone);
                            mb.setMember_ImageUri(downloadUrl);

                           // listOfMembers ito ung database reference . yung .add(fireStore query sa pag add) yung mb yung diniclare natin na class ng Members
                            listOfMembers.add(mb);

                            Snackbar.make(findViewById(android.R.id.content), "Member Added", Snackbar.LENGTH_LONG).show();

                            //in able ang button pag success "dito kasi yung process na on success listener ,success na ang process d2
                            imageView.setEnabled(true);
                            add_Member.setEnabled(true);

                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //dismiss natin yung progress dialog para di na umikot ng umikot kahit failed naman ang pagupload dito sa process na to
                    pd.dismiss();
                    //handle error
                    Toast.makeText(getApplicationContext(), "Unable to Upload", Toast.LENGTH_LONG).show();

                    //in able ang button pag success "dito kasi yung process na on failure listener ,failure na ang process d2
                    imageView.setEnabled(true);
                    add_Member.setEnabled(true);
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {


                    //rules while uploading on progress
                    double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());

                    //dis able ang button pag on progress pa lang para di sila magpipindot habang nagpa process "dito kasi yung process na on progress listener ,on going pa ang process d2
                    imageView.setEnabled(false);
                    add_Member.setEnabled(false);

                }
            });


        } else {

            //condition pag dinaanan niya ang buong process pero wala siyang maprocess kasi wala pala picture na nilagay pa imageview
            Snackbar.make(findViewById(android.R.id.content), "Add Photo Of Member.", Snackbar.LENGTH_LONG).show();

        }

    }


}


