package com.oytuntekesin.authenticationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import com.oytuntekesin.authenticationapp.databinding.ActivityMainBinding;
import com.oytuntekesin.authenticationapp.fragments.ExerciseFragment;
import com.oytuntekesin.authenticationapp.fragments.GlycoFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new GlycoFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.homeglyco) replaceFragment(new GlycoFragment());
            if (item.getItemId() == R.id.beslenme) replaceFragment(new BeslenmeFragment());
            if (item.getItemId() == R.id.egzersiz) replaceFragment(new ExerciseFragment());
            if (item.getItemId() == R.id.settings) replaceFragment(new SettingsFragment());

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