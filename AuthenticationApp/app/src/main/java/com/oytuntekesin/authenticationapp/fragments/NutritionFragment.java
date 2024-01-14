package com.oytuntekesin.authenticationapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
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
        Spinner spinUserNutrition = rootView.findViewById(R.id.spinUserNutrition);
        btnNutritionAdd.hide();
        spinUserNutrition.setVisibility(View.GONE);
        _db.collection("USER_TABLE").whereEqualTo("user_ID", _auth.getCurrentUser().getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                User c = d.toObject(User.class);
                                if (!c.getUSER_ROLE().equalsIgnoreCase("USER")){
                                    btnNutritionAdd.show();
                                    spinUserNutrition.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                });

        _userBusiness.getUserListForSpinner(_context, spinUserNutrition);
        _nutritionBusiness.getNutritionCardList(rootView, _auth.getCurrentUser().getUid());
        btnNutritionAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gecisYap = new Intent(getActivity().getApplicationContext(), AddNutritionActivity.class);
                startActivity(gecisYap);
            }
        });
        spinUserNutrition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                User selectedUser = (User) parentView.getItemAtPosition(position);
                _nutritionBusiness.getNutritionCardList(rootView, selectedUser.getUSER_ID());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Herhangi bir şey seçilmediğinde yapılacak işlemleri buraya ekleyebilirsiniz.
            }
        });
        return rootView;
    }

}