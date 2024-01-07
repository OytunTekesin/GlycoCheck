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
import com.oytuntekesin.authenticationapp.AddGlycoActivity;
import com.oytuntekesin.authenticationapp.R;
import com.oytuntekesin.authenticationapp.dto.ExerciseHistory;
import com.oytuntekesin.authenticationapp.dto.ExerciseTable;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {
    FirebaseFirestore _db;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView exerciseCard;
        public ImageView exerciseIcon;
        public TextView exerciseName;

        public ViewHolder(View view) {
            super(view);

            exerciseCard = (CardView)view.findViewById(R.id.pastExerciseCard);
            exerciseIcon = (ImageView) view.findViewById(R.id.pastExerciseIcon);
            exerciseName = (TextView)view.findViewById(R.id.pastExerciseName);
        }
    }

    List<ExerciseTable> exerciseList;
    public ExerciseAdapter(List<ExerciseTable> exerciseList) {

        this.exerciseList = exerciseList;
    }
    @Override
    public ExerciseAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_card, parent, false);
        final ExerciseAdapter.ViewHolder view_holder = new ExerciseAdapter.ViewHolder(v);
        v.setTag(view_holder.getPosition());
        return view_holder;
    }
    int p = 0;

    @Override
    public void onBindViewHolder(final ExerciseAdapter.ViewHolder holder, final int position) {
        Glide.with(holder.exerciseIcon.getContext())
                .load(exerciseList.get(position).getEXERCISE_URL())
                .into(holder.exerciseIcon);
        holder.exerciseName.setText(exerciseList.get(position).getEXERCISE_NAME());
        holder.exerciseCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.exerciseCard.getContext(), AddGlycoActivity.class);
                intent.putExtra("EXERCISE_ID", exerciseList.get(position).getEXERCISE_ID());
                intent.putExtra("EXERCISE_NAME", exerciseList.get(position).getEXERCISE_NAME());
                holder.exerciseCard.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}