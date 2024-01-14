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
import com.oytuntekesin.authenticationapp.dto.ExerciseHistory;
import com.oytuntekesin.authenticationapp.dto.Glyco;
import com.oytuntekesin.authenticationapp.dto.Nutrition;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NutritionBusiness extends BaseBusiness{
    public NutritionBusiness(){

    }
    public void getNutritionCardList(View rootView, String USER_ID){
        List<Nutrition> nutritionList = new ArrayList<Nutrition>();
        _db.collection("NUTRITION_TABLE").whereEqualTo("user_ID", USER_ID).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Nutrition c = d.toObject(Nutrition.class);
                                String tarih = c.getNUTRITION_DURATION();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                                try {
                                    Date date = dateFormat.parse(tarih);
                                    if (date.after(new Date())){
                                        c.setNUTRITION_ID(d.getId());
                                        nutritionList.add(c);
                                    }
                                } catch (ParseException e) {
                                    System.out.println("Parse hatası: " + e.getMessage());
                                }
                            }
                            NutritionAdapter adapter_items = new NutritionAdapter(nutritionList);

                            RecyclerView recycler_view = rootView.findViewById(R.id.nutritionList);
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
                            NutritionAdapter adapter_items = new NutritionAdapter(nutritionList);

                            RecyclerView recycler_view = rootView.findViewById(R.id.nutritionList);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(_context);

                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            layoutManager.scrollToPosition(0);

                            recycler_view.setLayoutManager(layoutManager);
                            recycler_view.setHasFixedSize(true);
                            recycler_view.setNestedScrollingEnabled(false);
                            recycler_view.setAdapter(adapter_items);
                            recycler_view.setItemAnimator(new DefaultItemAnimator());                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(rootView.getContext(), "Veri kaydı bulunamadı!", Toast.LENGTH_SHORT).show();
                        NutritionAdapter adapter_items = new NutritionAdapter(nutritionList);

                        RecyclerView recycler_view = rootView.findViewById(R.id.nutritionList);
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
    public boolean setNutritionData(Context context, Nutrition nutrition){
        if (nutrition.getNUTRITION_DURATION().isEmpty()){
            Toast.makeText(context, "Tarih bilgisi boş bırakılamaz", Toast.LENGTH_LONG).show();
            return false;
        }
        if (nutrition.getNUTRITION_NAME().isEmpty()){
            Toast.makeText(context, "Beslenme adı boş bırakılamaz", Toast.LENGTH_LONG).show();
            return false;
        }

        nutrition.setNUTRITION_DIETICIAN(_auth.getCurrentUser().getUid());
        if (nutrition.getNUTRITION_ID() != null && !nutrition.getNUTRITION_ID().isEmpty()){
            _db.collection("NUTRITION_TABLE").document(nutrition.getNUTRITION_ID()).set(nutrition).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(context, "Düzenlendi.", Toast.LENGTH_LONG).show();
                }
            });
        }else {
            _db.collection("NUTRITION_TABLE").document().set(nutrition).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(context, "Eklendi.", Toast.LENGTH_LONG).show();
                }
            });
        }

        return true;
    }

    public boolean delNutritionData(Context context, String NUTRITION_ID){
        if (!NUTRITION_ID.isEmpty()){
            _db.collection("NUTRITION_TABLE").document(NUTRITION_ID).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(context, "Silindi.", Toast.LENGTH_LONG).show();
                }
            });
        }
        return true;
    }
}
