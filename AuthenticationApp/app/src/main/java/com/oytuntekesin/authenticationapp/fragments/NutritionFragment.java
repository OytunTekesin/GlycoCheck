package com.oytuntekesin.authenticationapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.oytuntekesin.authenticationapp.AddGlycoActivity;
import com.oytuntekesin.authenticationapp.AddNutritionActivity;
import com.oytuntekesin.authenticationapp.R;
import com.oytuntekesin.authenticationapp.adapters.NutritionAdapter;
import com.oytuntekesin.authenticationapp.dto.Nutrition;
import com.oytuntekesin.authenticationapp.dto.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kotlinx.coroutines.channels.ChannelSegment;

public class NutritionFragment extends BaseFragment {
    public NutritionFragment() {
        // Boş kurucu metod gerekli
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Fragment'ın görünümünü oluştur
        View rootView = inflater.inflate(R.layout.fragment_nutrition, container, false);
        _context = rootView.getContext();

        FloatingActionButton btnNutritionAdd = rootView.findViewById(R.id.nutrition_add);
        btnNutritionAdd.hide();

        _db.collection("USER_TABLE").whereEqualTo("user_ID", _auth.getCurrentUser().getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                User cd = d.toObject(User.class);
                                _logonUser = cd;
                            }

                            List<Nutrition> nutritionList = new ArrayList<Nutrition>();
                            if (!_logonUser.getUSER_ROLE().equalsIgnoreCase("USER")){
                                btnNutritionAdd.show();
                                _db.collection("NUTRITION_TABLE").get()
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
                            }else{
                                _db.collection("NUTRITION_TABLE").whereEqualTo("user_ID", _logonUser.getUSER_ID()).get()
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
                    }
                });
        btnNutritionAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gecisYap = new Intent(getActivity().getApplicationContext(), AddNutritionActivity.class);
                startActivity(gecisYap);
            }
        });
        return rootView;
    }

}