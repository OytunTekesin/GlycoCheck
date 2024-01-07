package com.oytuntekesin.authenticationapp.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.oytuntekesin.authenticationapp.AddExerciseActivity;
import com.oytuntekesin.authenticationapp.AddGlycoActivity;
import com.oytuntekesin.authenticationapp.R;
import com.oytuntekesin.authenticationapp.dto.ExerciseHistory;
import com.oytuntekesin.authenticationapp.dto.ExerciseTable;

import java.util.List;

public class PastExerciseAdapter extends RecyclerView.Adapter<PastExerciseAdapter.ViewHolder> {
    FirebaseFirestore _db;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView pastExerciseCard;
        public ImageView pastExerciseIcon;
        public TextView pastExerciseName;
        public TextView pastExerciseDuration;
        public TextView pastExerciseBurnedCalories;
        public TextView pastExerciseDescriptionTextView;
        public TextView pastExerciseDateTime;


        public ViewHolder(View view) {
            super(view);

            pastExerciseCard = (CardView)view.findViewById(R.id.pastExerciseCard);
            pastExerciseIcon = (ImageView) view.findViewById(R.id.pastExerciseIcon);
            pastExerciseName = (TextView)view.findViewById(R.id.pastExerciseName);
            pastExerciseDuration = (TextView)view.findViewById(R.id.pastExerciseDuration);
            pastExerciseBurnedCalories = (TextView)view.findViewById(R.id.pastExerciseBurnedCalories);
            pastExerciseDescriptionTextView = (TextView)view.findViewById(R.id.pastExerciseDescriptionTextView);
            pastExerciseDateTime = (TextView)view.findViewById(R.id.pastExerciseDateTime);
        }
    }

    List<ExerciseHistory> pastExerciseList;
    public PastExerciseAdapter(List<ExerciseHistory> exerciseHistoryList) {

        this.pastExerciseList = exerciseHistoryList;
    }
    @Override
    public PastExerciseAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.past_exercise_card, parent, false);
        final PastExerciseAdapter.ViewHolder view_holder = new PastExerciseAdapter.ViewHolder(v);
        v.setTag(view_holder.getPosition());
        return view_holder;
    }
    int p = 0;

    @Override
    public void onBindViewHolder(final PastExerciseAdapter.ViewHolder holder, final int position) {
        String dbTarih = pastExerciseList.get(position).getEXERCISE_DATETIME().split(" ")[0];
        String[] tarihs = dbTarih.split("\\.");
        String tarih = "";
        switch (tarihs[1]){
            case "01":
                tarih = tarihs[0] + " Ocak " + tarihs[2];
                break;
            case "02":
                tarih = tarihs[0] + " Şubat "+ tarihs[2];
                break;
            case "03":
                tarih = tarihs[0] + " Mart "+ tarihs[2];
                break;
            case "04":
                tarih = tarihs[0] + " Nisan "+ tarihs[2];
                break;
            case "05":
                tarih = tarihs[0] + " Mayıs "+ tarihs[2];
                break;
            case "06":
                tarih = tarihs[0] + " Haziran "+ tarihs[2];
                break;
            case "07":
                tarih = tarihs[0] + " Temmuz "+ tarihs[2];
                break;
            case "08":
                tarih = tarihs[0] + " Ağustos "+ tarihs[2];
                break;
            case "09":
                tarih = tarihs[0] + " Eylül "+ tarihs[2];
                break;
            case "10":
                tarih = tarihs[0] + " Ekim "+ tarihs[2];
                break;
            case "11":
                tarih = tarihs[0] + " Kasım "+ tarihs[2];
                break;
            case "12":
                tarih = tarihs[0] + " Aralık "+ tarihs[2];
                break;
            default:
                tarih = dbTarih;
                break;
        }
        _db = FirebaseFirestore.getInstance();
        _db.collection("EXERCISE_TABLE").document(pastExerciseList.get(position).getEXERCISE_ID()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        ExerciseTable exerciseTable = documentSnapshot.toObject(ExerciseTable.class);
                        Glide.with(holder.pastExerciseIcon.getContext())
                                .load(exerciseTable.getEXERCISE_URL())
                                .into(holder.pastExerciseIcon);
                        holder.pastExerciseName.setText(exerciseTable.getEXERCISE_NAME());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // if we do not get any data or any error we are displaying
                // a toast message that we do not get any data
                Toast.makeText(holder.pastExerciseCard.getContext(), "Fail to get the data.", Toast.LENGTH_SHORT).show();
            }
        });

        holder.pastExerciseDescriptionTextView.setText(pastExerciseList.get(position).getEXERCISE_DESCR());
        holder.pastExerciseDuration.setText(pastExerciseList.get(position).getEXERCISE_DURATION());
        holder.pastExerciseBurnedCalories.setText(pastExerciseList.get(position).getEXERCISE_CALORIES());
        holder.pastExerciseDateTime.setText(tarih + " " + pastExerciseList.get(position).getEXERCISE_DATETIME().split(" ")[1]);
        holder.pastExerciseCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.pastExerciseCard.getContext(), AddExerciseActivity.class);
                intent.putExtra("EXERCISE_HISTORY_ID", pastExerciseList.get(position).getEXERCISE_HISTORY_ID());
                intent.putExtra("EXERCISE_ID", pastExerciseList.get(position).getEXERCISE_ID());
                intent.putExtra("EXERCISE_NAME", pastExerciseList.get(position).getEXERCISE_NAME());
                intent.putExtra("EXERCISE_DURATION", pastExerciseList.get(position).getEXERCISE_DURATION());
                intent.putExtra("EXERCISE_CALORIES", pastExerciseList.get(position).getEXERCISE_CALORIES());
                intent.putExtra("EXERCISE_DESCR", pastExerciseList.get(position).getEXERCISE_DESCR());
                intent.putExtra("EXERCISE_DATETIME", pastExerciseList.get(position).getEXERCISE_DATETIME());
                holder.pastExerciseCard.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pastExerciseList.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}