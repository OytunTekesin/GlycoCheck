package com.oytuntekesin.authenticationapp.tabs.exercise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.oytuntekesin.authenticationapp.R;

public class TabMyExercises extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.tab_my_exercises, container, false);
        return root;
    }
}