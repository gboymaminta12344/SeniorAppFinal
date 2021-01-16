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

public class RemarksAdapter extends FirestoreRecyclerAdapter<GiveMedicinItem, RemarksAdapter.RemarksHolder> {
    private Context context;

    public RemarksAdapter(@NonNull FirestoreRecyclerOptions<GiveMedicinItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RemarksHolder holder, int position, @NonNull GiveMedicinItem model) {

        holder.date_of_rel.setText(model.getTimestamp().toString());
        holder.medicine_name.setText(model.getMemberMedicineDescription());
        holder.Senior_Name.setText(model.getFullName());



        holder.BTN_Binigay_Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //giving the id of the member and view in much detailed
                String given_date = model.getTimestamp().toString();
                String id = getSnapshots().getSnapshot(position).getId();
                Intent intent = new Intent(v.getContext(), BinigayNaGamot.class);
                intent.putExtra("id", id);
                intent.putExtra("given_date",given_date);
                v.getContext().startActivity(intent);




            }
        });


    }

    @NonNull
    @Override
    public RemarksHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        //dito ang layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.remarks, parent, false);
        context = parent.getContext();
        return new RemarksAdapter.RemarksHolder(view);

    }

    class RemarksHolder extends RecyclerView.ViewHolder {

        TextView date_of_rel;
        TextView medicine_name;
        TextView Senior_Name;
        Button BTN_Binigay_Info;

        public RemarksHolder(@NonNull View itemView) {
            super(itemView);

            date_of_rel = itemView.findViewById(R.id.text_display_date);
            medicine_name = itemView.findViewById(R.id.text_display_med_name);
            Senior_Name = itemView.findViewById(R.id.text_display_medicine_note);
            BTN_Binigay_Info = itemView.findViewById(R.id.View_Binigay_Info);
        }
    }
}
