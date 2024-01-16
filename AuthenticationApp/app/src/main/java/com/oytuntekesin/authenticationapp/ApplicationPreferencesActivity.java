package com.oytuntekesin.authenticationapp;

import android.os.Bundle;

public class ApplicationPreferencesActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_preferences);
        _context = ApplicationPreferencesActivity.this;

    }
}
