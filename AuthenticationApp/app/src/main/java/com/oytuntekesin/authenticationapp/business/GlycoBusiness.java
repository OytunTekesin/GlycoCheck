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
import com.oytuntekesin.authenticationapp.dto.Glyco;

import java.util.ArrayList;
import java.util.List;

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
        if (glyco.getID() != null && !glyco.getID().isEmpty()){
            _db.collection("GLYCO_TABLE").document(glyco.getID()).set(glyco).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(context, "Düzenlendi.", Toast.LENGTH_LONG).show();
                }
            });
        }else {
            _db.collection("GLYCO_TABLE").document().set(glyco).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(context, "Eklendi.", Toast.LENGTH_LONG).show();
                }
            });
        }

        return true;
    }

    public void getGlycoCardList(View rootview){
        List<Glyco> glycoList = new ArrayList<Glyco>();
        _db.collection("GLYCO_TABLE").whereEqualTo("user_ID", _auth.getCurrentUser().getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Glyco c = d.toObject(Glyco.class);
                                c.setID(d.getId());
                                glycoList.add(c);
                            }
                            GlycoAdapter adapter_items = new GlycoAdapter(glycoList);

                            RecyclerView recycler_view = rootview.findViewById(R.id.glycoItemList);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(_context);

                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            layoutManager.scrollToPosition(0);

                            recycler_view.setLayoutManager(layoutManager);
                            recycler_view.setHasFixedSize(true);
                            recycler_view.setNestedScrollingEnabled(false);
                            recycler_view.setAdapter(adapter_items);
                            recycler_view.setItemAnimator(new DefaultItemAnimator());
                        } else {
                            Toast.makeText(_context, "Veri kaydı bulunamadı!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // if we do not get any data or any error we are displaying
                        // a toast message that we do not get any data
                        Toast.makeText(_context, "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
