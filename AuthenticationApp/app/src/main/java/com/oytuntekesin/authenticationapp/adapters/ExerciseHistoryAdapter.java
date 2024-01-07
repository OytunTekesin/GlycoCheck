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
import com.oytuntekesin.authenticationapp.DegerEkleActivity;
import com.oytuntekesin.authenticationapp.R;
import com.oytuntekesin.authenticationapp.dto.ExerciseHistory;
import com.oytuntekesin.authenticationapp.dto.ExerciseTable;

import java.util.List;

public class ExerciseHistoryAdapter extends RecyclerView.Adapter<ExerciseHistoryAdapter.ViewHolder> {
    FirebaseFirestore _db;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView exerciseCard;
        public ImageView exerciseIcon;
        public TextView exerciseName;
        public TextView exerciseDuration;
        public TextView burnedCalories;
        public TextView descriptionTextView;
        public TextView exerciseDateTime;


        public ViewHolder(View view) {
            super(view);

            exerciseCard = (CardView)view.findViewById(R.id.exerciseCard);
            exerciseIcon = (ImageView) view.findViewById(R.id.exerciseIcon);
            exerciseName = (TextView)view.findViewById(R.id.exerciseName);
            exerciseDuration = (TextView)view.findViewById(R.id.exerciseDuration);
            burnedCalories = (TextView)view.findViewById(R.id.burnedCalories);
            descriptionTextView = (TextView)view.findViewById(R.id.descriptionTextView);
            exerciseDateTime = (TextView)view.findViewById(R.id.exerciseDateTime);
        }
    }

    List<ExerciseHistory> exerciseHistoryList;
    public ExerciseHistoryAdapter(List<ExerciseHistory> exerciseHistoryList) {

        this.exerciseHistoryList = exerciseHistoryList;
    }
    @Override
    public ExerciseHistoryAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_card, parent, false);
        final ExerciseHistoryAdapter.ViewHolder view_holder = new ExerciseHistoryAdapter.ViewHolder(v);
        v.setTag(view_holder.getPosition());
        return view_holder;
    }
    int p = 0;

    @Override
    public void onBindViewHolder(final ExerciseHistoryAdapter.ViewHolder holder, final int position) {
        String dbTarih = exerciseHistoryList.get(position).getEXERCISE_DATETIME().split(" ")[0];
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
        _db.collection("EXERCISE_TABLE").document(exerciseHistoryList.get(position).getEXERCISE_ID()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        ExerciseTable exerciseTable = documentSnapshot.toObject(ExerciseTable.class);
                        Glide.with(holder.exerciseIcon.getContext())
                                .load(exerciseTable.getEXERCISE_URL())
                                .into(holder.exerciseIcon);
                        holder.exerciseName.setText(exerciseTable.getEXERCISE_ADI());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // if we do not get any data or any error we are displaying
                // a toast message that we do not get any data
                Toast.makeText(holder.exerciseCard.getContext(), "Fail to get the data.", Toast.LENGTH_SHORT).show();
            }
        });

        holder.descriptionTextView.setText(exerciseHistoryList.get(position).getEXERCISE_DESCR());
        holder.exerciseDuration.setText(exerciseHistoryList.get(position).getEXERCISE_DURATION());
        holder.burnedCalories.setText(exerciseHistoryList.get(position).getEXERCISE_CALORIES());
        holder.exerciseDateTime.setText(tarih);
        holder.exerciseCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.exerciseCard.getContext(), DegerEkleActivity.class);
                intent.putExtra("EXERCISE_HISTORY_ID", exerciseHistoryList.get(position).getEXERCISE_HISTORY_ID());
                intent.putExtra("EXERCISE_ID", exerciseHistoryList.get(position).getEXERCISE_ID());
                intent.putExtra("EXERCISE_NAME", exerciseHistoryList.get(position).getEXERCISE_NAME());
                intent.putExtra("EXERCISE_DURATION", exerciseHistoryList.get(position).getEXERCISE_DURATION());
                intent.putExtra("EXERCISE_CALORIES", exerciseHistoryList.get(position).getEXERCISE_CALORIES());
                intent.putExtra("EXERCISE_DESCR", exerciseHistoryList.get(position).getEXERCISE_DESCR());
                intent.putExtra("EXERCISE_DATETIME", exerciseHistoryList.get(position).getEXERCISE_DATETIME());
                holder.exerciseCard.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exerciseHistoryList.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}