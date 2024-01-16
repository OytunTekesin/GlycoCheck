package com.oytuntekesin.authenticationapp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.oytuntekesin.authenticationapp.databinding.ActivityMainBinding;
import com.oytuntekesin.authenticationapp.fragments.ExerciseFragment;
import com.oytuntekesin.authenticationapp.fragments.GlycoFragment;
import com.oytuntekesin.authenticationapp.fragments.NutritionFragment;
import com.oytuntekesin.authenticationapp.fragments.SettingsFragment;

public class MainActivity extends BaseActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        replaceFragment(new GlycoFragment());
        if (intent.getStringExtra("TAB") == null){
            replaceFragment(new GlycoFragment());
        }else{
            if (intent.getStringExtra("TAB").equals("0")){
                binding.bottomNavigationView.getMenu().getItem(0).setChecked(true);
                replaceFragment(new GlycoFragment());
            }else if(intent.getStringExtra("TAB").equals("1")){
                binding.bottomNavigationView.getMenu().getItem(1).setChecked(true);
                replaceFragment(new ExerciseFragment());
            }else if(intent.getStringExtra("TAB").equals("2")){
                binding.bottomNavigationView.getMenu().getItem(2).setChecked(true);
                replaceFragment(new NutritionFragment());
            }else if(intent.getStringExtra("TAB").equals("3")){
                binding.bottomNavigationView.getMenu().getItem(3).setChecked(true);
                replaceFragment(new SettingsFragment());
            }
        }



        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.homeglyco) {
                replaceFragment(new GlycoFragment());
            }
            if (item.getItemId() == R.id.beslenme){
                replaceFragment(new NutritionFragment());
            }
            if (item.getItemId() == R.id.egzersiz){
                replaceFragment(new ExerciseFragment());
            }
            if (item.getItemId() == R.id.settings){
                replaceFragment(new SettingsFragment());
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}