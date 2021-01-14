package com.android2.seniorappclone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        holder.medicine_note.setText(model.getMemberMedicineNote());

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
        TextView medicine_note;

        public RemarksHolder(@NonNull View itemView) {
            super(itemView);

            date_of_rel = itemView.findViewById(R.id.text_display_date);
            medicine_name = itemView.findViewById(R.id.text_display_med_name);
            medicine_note = itemView.findViewById(R.id.text_display_medicine_note);
        }
    }
}
