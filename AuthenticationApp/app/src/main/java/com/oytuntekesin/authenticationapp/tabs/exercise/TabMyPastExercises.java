package com.oytuntekesin.authenticationapp.tabs.exercise;

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
import com.oytuntekesin.authenticationapp.R;
import com.oytuntekesin.authenticationapp.adapters.PastExerciseAdapter;
import com.oytuntekesin.authenticationapp.dto.ExerciseHistory;
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

        FloatingActionButton btnAddMyPastExercise;
        btnAddMyPastExercise = rootView.findViewById(R.id.add_my_past_exercise);

        List<ExerciseHistory> exerciseHistoryList = new ArrayList<ExerciseHistory>();
        _db.collection("EXERCISE_HISTORY").whereEqualTo("user_ID", _auth.getCurrentUser().getUid()).get()
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

        btnAddMyPastExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gecisYap = new Intent(getActivity().getApplicationContext(), AddGlycoActivity.class);
                startActivity(gecisYap);
            }
        });

        return rootView;
    }
}
