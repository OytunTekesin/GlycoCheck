package com.oytuntekesin.authenticationapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oytuntekesin.authenticationapp.AccountSettingsActivity;
import com.oytuntekesin.authenticationapp.ApplicationPreferencesActivity;
import com.oytuntekesin.authenticationapp.LoginActivity;
import com.oytuntekesin.authenticationapp.ProfileSettingsActivity;
import com.oytuntekesin.authenticationapp.R;
import com.oytuntekesin.authenticationapp.fragments.BaseFragment;

public class SettingsFragment extends BaseFragment {

    public SettingsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        _context = rootView.getContext();
        TextView btnProfileSettings = rootView.findViewById(R.id.btnProfileSettings);
        TextView btnAccountSettings = rootView.findViewById(R.id.btnAccountSettings);
        TextView btnApplicationSettings = rootView.findViewById(R.id.btnApplicationSettings);
        TextView btnLogout = rootView.findViewById(R.id.btnLogout);


        btnProfileSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gecisYap = new Intent(_context, ProfileSettingsActivity.class);
                startActivity(gecisYap);
            }
        });
        btnAccountSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gecisYap = new Intent(_context, AccountSettingsActivity.class);
                startActivity(gecisYap);
            }
        });
        btnApplicationSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gecisYap = new Intent(_context, ApplicationPreferencesActivity.class);
                startActivity(gecisYap);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _auth.signOut();
                Intent gecisYap = new Intent(_context, LoginActivity.class);
                startActivity(gecisYap);
            }
        });

        return rootView;
    }
}