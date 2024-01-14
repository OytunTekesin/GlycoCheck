package com.oytuntekesin.authenticationapp.business;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.oytuntekesin.authenticationapp.dto.ExerciseHistory;
import com.oytuntekesin.authenticationapp.dto.Nutrition;

public class NutritionBusiness extends BaseBusiness{
    public NutritionBusiness(){

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
}
