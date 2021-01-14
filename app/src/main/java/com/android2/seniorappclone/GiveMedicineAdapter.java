package com.android2.seniorappclone;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

public class GiveMedicineAdapter extends FirestoreRecyclerAdapter<Members, GiveMedicineAdapter.GiveMedicineHolder> {



    private Context context;
    //firebase Ini
    FirebaseStorage storage = FirebaseStorage.getInstance();
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    //firebase Ini
    DocumentReference df;
    String status;


    public GiveMedicineAdapter(@NonNull FirestoreRecyclerOptions<Members> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull GiveMedicineHolder holder, int position, @NonNull Members model) {


        //holder
        holder.viewNameSenior.setText(model.getFullname());
        holder.viewStatusSenior.setText(model.getStatus());

        //click to give medication
        holder.BTN_member_medication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //giving the id of the member and view in much detailed

                String id = getSnapshots().getSnapshot(position).getId();
                Intent intent = new Intent(v.getContext(), Medication.class);
                intent.putExtra("id", id);
                v.getContext().startActivity(intent);


            }
        });


    }

    @NonNull
    @Override
    public GiveMedicineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //dito ang layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.give_member_medicine, parent, false);
        context = parent.getContext();
        return new GiveMedicineAdapter.GiveMedicineHolder(view);

    }

    class GiveMedicineHolder extends RecyclerView.ViewHolder {

        TextView viewNameSenior;
        TextView viewStatusSenior;
        Button BTN_member_medication;

        public GiveMedicineHolder(@NonNull View itemView) {
            super(itemView);

            viewNameSenior = itemView.findViewById(R.id.text_display_MName);
            viewStatusSenior = itemView.findViewById(R.id.text_display_status);
            BTN_member_medication = itemView.findViewById(R.id.add_medication);
        }
    }
}
