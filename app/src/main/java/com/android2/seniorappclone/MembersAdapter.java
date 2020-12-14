package com.android2.seniorappclone;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

public class MembersAdapter extends FirestoreRecyclerAdapter<Members, MembersAdapter.MembersHolder> {
    private static final String TAG = null;
    private Context context;
    //firebase Ini
    FirebaseStorage storage = FirebaseStorage.getInstance();
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    //firebase Ini
    DocumentReference df;
    String status;

    public MembersAdapter(@NonNull FirestoreRecyclerOptions<Members> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MembersHolder holder, int position, @NonNull Members model) {

        //dito ang operation

        //holder
        holder.viewName.setText(model.getFullname());
        holder.viewAdress.setText("Brgy " + " " + model.getBarangay() + ",  " + model.getMunicipality() + ",   Zone: " + model.getZone());
        Picasso.get().load(model.getMember_ImageUri()).into(holder.MyfetchImage);

        // click the Button
        holder.BTN_member_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the id and toss it update page
                String id = getSnapshots().getSnapshot(position).getId();
                Intent intent = new Intent(v.getContext(), UpadateMember.class);
                intent.putExtra("id", id);
                v.getContext().startActivity(intent);


            }
        });


    }

    @NonNull
    @Override
    public MembersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //dito ang layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.members_item, parent, false);
        context = parent.getContext();
        return new MembersHolder(view);

    }

    class MembersHolder extends RecyclerView.ViewHolder {

        TextView viewName;
        TextView viewAdress;
        ImageView MyfetchImage;
        Button BTN_member_information;


        public MembersHolder(@NonNull View itemView) {
            super(itemView);

            viewName = itemView.findViewById(R.id.card_display_MName);
            viewAdress = itemView.findViewById(R.id.card_display_MAdd);
            MyfetchImage = itemView.findViewById(R.id.member_fetch_image);
            BTN_member_information = itemView.findViewById(R.id.BTN_full_details);
        }
    }
}
