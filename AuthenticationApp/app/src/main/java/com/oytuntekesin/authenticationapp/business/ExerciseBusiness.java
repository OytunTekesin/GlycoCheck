package com.oytuntekesin.authenticationapp.business;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.oytuntekesin.authenticationapp.dto.ExerciseHistory;
import com.oytuntekesin.authenticationapp.dto.Glyco;

public class ExerciseBusiness extends BaseBusiness{
    public ExerciseBusiness(){

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
}
