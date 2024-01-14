package com.oytuntekesin.authenticationapp.tabs.exercise;

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
import com.oytuntekesin.authenticationapp.R;
import com.oytuntekesin.authenticationapp.adapters.PastExerciseAdapter;
import com.oytuntekesin.authenticationapp.dto.ExerciseHistory;
import com.oytuntekesin.authenticationapp.dto.User;
import com.oytuntekesin.authenticationapp.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class TabMyPastExercises extends BaseFragment {
    public TabMyPastExercises() {
        // Boş kurucu metod gerekli
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_my_past_exercises, container, false);
        _context = rootView.getContext();

//        FloatingActionButton btnAddMyPastExercise = rootView.findViewById(R.id.add_my_past_exercise);
        Spinner spinUserExercise = rootView.findViewById(R.id.spinUserExercise);
        spinUserExercise.setVisibility(View.GONE);
        _db.collection("USER_TABLE").whereEqualTo("user_ID", _auth.getCurrentUser().getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                User c = d.toObject(User.class);
                                if (!c.getUSER_ROLE().equalsIgnoreCase("USER")){
                                    spinUserExercise.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                });


        _userBusiness.getUserListForSpinner(_context, spinUserExercise);
        _exerciseBusiness.getExerciseHistoryCardList(rootView, _auth.getCurrentUser().getUid());


//        btnAddMyPastExercise.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
        spinUserExercise.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                User selectedUser = (User) parentView.getItemAtPosition(position);
                _exerciseBusiness.getExerciseHistoryCardList(rootView, selectedUser.getUSER_ID());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
        return rootView;
    }
}
