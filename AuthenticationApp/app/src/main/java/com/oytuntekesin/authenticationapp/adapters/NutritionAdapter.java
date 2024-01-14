package com.oytuntekesin.authenticationapp.adapters;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.oytuntekesin.authenticationapp.AddExerciseActivity;
import com.oytuntekesin.authenticationapp.AddGlycoActivity;
import com.oytuntekesin.authenticationapp.AddNutritionActivity;
import com.oytuntekesin.authenticationapp.NutritionContentActivity;
import com.oytuntekesin.authenticationapp.R;
import com.oytuntekesin.authenticationapp.dto.Glyco;
import com.oytuntekesin.authenticationapp.dto.Nutrition;
import com.oytuntekesin.authenticationapp.dto.User;

import java.util.Date;
import java.util.List;

public class NutritionAdapter extends RecyclerView.Adapter<NutritionAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView nutritionCard;
        public TextView dietitianName;
        public TextView duration;
        public TextView totalCalories;
        public TextView dietName;
        public ImageView dietIcon;


        public ViewHolder(View view) {
            super(view);

            nutritionCard = (CardView)view.findViewById(R.id.nutritionCard);
            dietitianName = (TextView) view.findViewById(R.id.dietitianName);
            duration = (TextView)view.findViewById(R.id.duration);
            totalCalories = (TextView)view.findViewById(R.id.totalCalories);
            dietName = (TextView)view.findViewById(R.id.dietName);
            dietIcon = (ImageView)view.findViewById(R.id.dietIcon);
        }
    }
    List<Nutrition> nutritionList;
    public NutritionAdapter(List<Nutrition> nutritionList) {

        this.nutritionList = nutritionList;
    }
    @Override
    public NutritionAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nutrition_card, parent, false);
        final NutritionAdapter.ViewHolder view_holder = new NutritionAdapter.ViewHolder(v);
        v.setTag(view_holder.getPosition());
        return view_holder;
    }
    @Override
    public void onBindViewHolder(final NutritionAdapter.ViewHolder holder, final int position) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        holder.duration.setText("Diyet Bitiş:" + nutritionList.get(position).getNUTRITION_DURATION().toString());
        holder.totalCalories.setText("Toplam Kalori:" +  nutritionList.get(position).getNUTRITION_CAL() + "cal");
        holder.dietName.setText(nutritionList.get(position).getNUTRITION_NAME());

        db.collection("USER_TABLE").whereEqualTo("user_ID", nutritionList.get(position).getNUTRITION_DIETICIAN()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                User cd = d.toObject(User.class);
                                holder.dietitianName.setText("Dyt." + cd.getUSER_ADI());
                            }
                        } else {
                            Toast.makeText(holder.nutritionCard.getContext(), "Veri kaydı bulunamadı!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // if we do not get any data or any error we are displaying
                        // a toast message that we do not get any data
                        Toast.makeText(holder.nutritionCard.getContext(), "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    }
                });

        holder.nutritionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("USER_TABLE").whereEqualTo("user_ID", auth.getCurrentUser().getUid()).get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                    for (DocumentSnapshot d : list) {
                                        if (d.get("user_ROLE").toString().equalsIgnoreCase("USER")){
                                            Intent intent = new Intent(holder.nutritionCard.getContext(), NutritionContentActivity.class);
                                            intent.putExtra("NUTRITION_ID", nutritionList.get(position).getNUTRITION_ID());
                                            intent.putExtra("NUTRITION_NAME", nutritionList.get(position).getNUTRITION_NAME());
                                            intent.putExtra("NUTRITION_DURATION", nutritionList.get(position).getNUTRITION_DURATION());
                                            intent.putExtra("NUTRITION_CONTENT", nutritionList.get(position).getNUTRITION_CONTENT());
                                            intent.putExtra("USER_ID", nutritionList.get(position).getUSER_ID());
                                            holder.nutritionCard.getContext().startActivity(intent);
                                        }else {
                                            Intent intent = new Intent(holder.nutritionCard.getContext(), AddNutritionActivity.class);
                                            intent.putExtra("NUTRITION_ID", nutritionList.get(position).getNUTRITION_ID());
                                            intent.putExtra("NUTRITION_NAME", nutritionList.get(position).getNUTRITION_NAME());
                                            intent.putExtra("NUTRITION_DURATION", nutritionList.get(position).getNUTRITION_DURATION());
                                            intent.putExtra("NUTRITION_CONTENT", nutritionList.get(position).getNUTRITION_CONTENT());
                                            intent.putExtra("USER_ID", nutritionList.get(position).getUSER_ID());
                                            holder.nutritionCard.getContext().startActivity(intent);
                                        }
                                    }
                                } else {
                                    Toast.makeText(view.getContext(), "Veri kaydı bulunamadı!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // if we do not get any data or any error we are displaying
                                // a toast message that we do not get any data
                                Toast.makeText(view.getContext(), "Fail to get the data.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return nutritionList.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
