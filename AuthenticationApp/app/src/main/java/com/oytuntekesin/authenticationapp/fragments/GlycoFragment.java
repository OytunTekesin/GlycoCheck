package com.oytuntekesin.authenticationapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.oytuntekesin.authenticationapp.adapters.GlycoAdapter;
import com.oytuntekesin.authenticationapp.dto.Glyco;
import com.oytuntekesin.authenticationapp.dto.User;

import java.util.ArrayList;
import java.util.List;

public class GlycoFragment extends BaseFragment {
    public GlycoFragment() {
        // Boş kurucu metod gerekli
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Fragment'ın görünümünü oluştur
        View rootView = inflater.inflate(R.layout.fragment_glyco, container, false);
        _context = rootView.getContext();

        FloatingActionButton btnGlycoAdd = rootView.findViewById(R.id.btnGlycoAdd);
        Spinner spinUserGlyco = rootView.findViewById(R.id.spinUserGlyco);
        spinUserGlyco.setVisibility(View.GONE);

        _db.collection("USER_TABLE").whereEqualTo("user_ID", _auth.getCurrentUser().getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                User c = d.toObject(User.class);
                                if (!c.getUSER_ROLE().equalsIgnoreCase("USER")){
                                    btnGlycoAdd.hide();
                                    spinUserGlyco.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                });

        _userBusiness.getUserListForSpinner(_context, spinUserGlyco);
        _glycoBusiness.getGlycoCardList(rootView, _auth.getCurrentUser().getUid());



        btnGlycoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gecisYap = new Intent(getActivity().getApplicationContext(), AddGlycoActivity.class);
                startActivity(gecisYap);
            }
        });

        spinUserGlyco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                User selectedUser = (User) parentView.getItemAtPosition(position);
                _glycoBusiness.getGlycoCardList(rootView, selectedUser.getUSER_ID());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Herhangi bir şey seçilmediğinde yapılacak işlemleri buraya ekleyebilirsiniz.
            }
        });
        return rootView;
    }

}
