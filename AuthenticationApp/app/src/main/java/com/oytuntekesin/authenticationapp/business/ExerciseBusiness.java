package com.oytuntekesin.authenticationapp.business;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.oytuntekesin.authenticationapp.R;
import com.oytuntekesin.authenticationapp.adapters.GlycoAdapter;
import com.oytuntekesin.authenticationapp.adapters.NutritionAdapter;
import com.oytuntekesin.authenticationapp.adapters.PastExerciseAdapter;
import com.oytuntekesin.authenticationapp.dto.ExerciseHistory;
import com.oytuntekesin.authenticationapp.dto.Glyco;

import java.util.ArrayList;
import java.util.List;

public class ExerciseBusiness extends BaseBusiness{
    public ExerciseBusiness(){

    }

    public void getExerciseHistoryCardList(View rootView, String USER_ID){
        List<ExerciseHistory> exerciseHistoryList = new ArrayList<ExerciseHistory>();
        _db.collection("EXERCISE_HISTORY").whereEqualTo("user_ID", USER_ID).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                ExerciseHistory c = d.toObject(ExerciseHistory.class);
                                c.setEXERCISE_HISTORY_ID(d.getId());
                                exerciseHistoryList.add(c);
                            }
                            PastExerciseAdapter adapter_items = new PastExerciseAdapter(exerciseHistoryList);

                            RecyclerView recycler_view = rootView.findViewById(R.id.my_past_exercises);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(_context);

                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            layoutManager.scrollToPosition(0);

                            recycler_view.setLayoutManager(layoutManager);
                            recycler_view.setHasFixedSize(true);
                            recycler_view.setNestedScrollingEnabled(false);
                            recycler_view.setAdapter(adapter_items);
                            recycler_view.setItemAnimator(new DefaultItemAnimator());
                        } else {
                            Toast.makeText(rootView.getContext(), "Veri kaydı bulunamadı!", Toast.LENGTH_SHORT).show();
                            PastExerciseAdapter adapter_items = new PastExerciseAdapter(exerciseHistoryList);

                            RecyclerView recycler_view = rootView.findViewById(R.id.my_past_exercises);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(_context);

                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            layoutManager.scrollToPosition(0);

                            recycler_view.setLayoutManager(layoutManager);
                            recycler_view.setHasFixedSize(true);
                            recycler_view.setNestedScrollingEnabled(false);
                            recycler_view.setAdapter(adapter_items);
                            recycler_view.setItemAnimator(new DefaultItemAnimator());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(rootView.getContext(), "Veri kaydı bulunamadı!", Toast.LENGTH_SHORT).show();
                        PastExerciseAdapter adapter_items = new PastExerciseAdapter(exerciseHistoryList);

                        RecyclerView recycler_view = rootView.findViewById(R.id.my_past_exercises);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(_context);

                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        layoutManager.scrollToPosition(0);

                        recycler_view.setLayoutManager(layoutManager);
                        recycler_view.setHasFixedSize(true);
                        recycler_view.setNestedScrollingEnabled(false);
                        recycler_view.setAdapter(adapter_items);
                        recycler_view.setItemAnimator(new DefaultItemAnimator());
                    }
                });
    }

    public boolean setExerciseHistoryData(Context context, ExerciseHistory exerciseHistory){
        if (exerciseHistory.getEXERCISE_ID().isEmpty()){
            Toast.makeText(context, "Egzersiz bilgisi boş bırakılamaz", Toast.LENGTH_LONG).show();
            return false;
        }
        if (exerciseHistory.getEXERCISE_DATETIME().isEmpty()){
            Toast.makeText(context, "Tarih bilgisi boş bırakılamaz", Toast.LENGTH_LONG).show();
            return false;
        }

        exerciseHistory.setUSER_ID(_auth.getCurrentUser().getUid());
        if (exerciseHistory.getEXERCISE_HISTORY_ID() != null && !exerciseHistory.getEXERCISE_HISTORY_ID().isEmpty()){
            _db.collection("EXERCISE_HISTORY").document(exerciseHistory.getEXERCISE_HISTORY_ID()).set(exerciseHistory).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(context, "Düzenlendi.", Toast.LENGTH_LONG).show();
                }
            });
        }else {
            _db.collection("EXERCISE_HISTORY").document().set(exerciseHistory).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(context, "Eklendi.", Toast.LENGTH_LONG).show();
                }
            });
        }

        return true;
    }

    public boolean delExerciseData(Context context, String EXERCISE_HISTORY_ID){
        if (!EXERCISE_HISTORY_ID.isEmpty()){
            _db.collection("EXERCISE_HISTORY").document(EXERCISE_HISTORY_ID).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(context, "Silindi.", Toast.LENGTH_LONG).show();
                }
            });
        }
        return true;
    }

}
