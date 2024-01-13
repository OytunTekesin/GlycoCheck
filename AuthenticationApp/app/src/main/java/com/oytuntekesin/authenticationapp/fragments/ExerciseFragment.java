package com.oytuntekesin.authenticationapp.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.oytuntekesin.authenticationapp.R;
import com.oytuntekesin.authenticationapp.adapters.ViewPagerAdapter;
import com.oytuntekesin.authenticationapp.tabs.exercise.TabMyExercises;
import com.oytuntekesin.authenticationapp.tabs.exercise.TabMyPastExercises;

public class ExerciseFragment extends BaseFragment {

    public ExerciseFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_exercise, container, false);
        _context = rootView.getContext();

        ViewPager viewPager = rootView.findViewById(R.id.exerciseViewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.FragmentEkle(new TabMyPastExercises(), "Geçmiş Egzersizler");
        adapter.FragmentEkle(new TabMyExercises(), "Egzersizlerim");

        viewPager.setAdapter(adapter);

        TabLayout tabLayout = rootView.findViewById(R.id.exerciseTabs);
        tabLayout.setupWithViewPager(viewPager);

//        tabLayout.getTabAt(0);
//        tabLayout.getTabAt(1);
//        tabLayout.getTabAt(2);

        return rootView;
    }
}