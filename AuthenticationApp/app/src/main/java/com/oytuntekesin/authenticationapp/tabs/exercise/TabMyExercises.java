package com.oytuntekesin.authenticationapp.tabs.exercise;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import com.oytuntekesin.authenticationapp.adapters.ExerciseAdapter;
import com.oytuntekesin.authenticationapp.adapters.PastExerciseAdapter;
import com.oytuntekesin.authenticationapp.dto.ExerciseHistory;
import com.oytuntekesin.authenticationapp.dto.ExerciseTable;
import com.oytuntekesin.authenticationapp.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class TabMyExercises extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_my_exercises, container, false);
        _context = rootView.getContext();

        List<ExerciseTable> exerciseList = new ArrayList<ExerciseTable>();
        _db.collection("EXERCISE_TABLE").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                ExerciseTable c = d.toObject(ExerciseTable.class);
                                exerciseList.add(c);
                            }
                            ExerciseAdapter adapter_items = new ExerciseAdapter(exerciseList);

                            RecyclerView recycler_view = rootView.findViewById(R.id.my_exercises);
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
        return rootView;
    }
}