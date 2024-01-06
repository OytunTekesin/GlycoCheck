package com.oytuntekesin.authenticationapp.business;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.oytuntekesin.authenticationapp.dto.Glyco;

public class GlycoBusiness extends BaseBusiness{
    public GlycoBusiness(){

    }
    public boolean setGlycoData(Context context, Glyco glyco){
        if (glyco.getKAN_SEKERI().isEmpty()){
            Toast.makeText(context, "Kan Şekeri bilgisi boş bırakılamaz", Toast.LENGTH_LONG).show();
            return false;
        }
        if (glyco.getTARIH().isEmpty()){
            Toast.makeText(context, "Tarih bilgisi boş bırakılamaz", Toast.LENGTH_LONG).show();
            return false;
        }

        glyco.setUSER_ID(_auth.getCurrentUser().getUid());
        if (glyco.getID() == null){
            _db.collection("GLYCO_TABLE").document().set(glyco).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(context, "Eklendi.", Toast.LENGTH_LONG).show();
                }
            });
        }else {
            _db.collection("GLYCO_TABLE").document(glyco.getID()).set(glyco).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(context, "Düzenlendi.", Toast.LENGTH_LONG).show();
                }
            });
        }

        return true;
    }
}
