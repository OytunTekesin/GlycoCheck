package com.oytuntekesin.authenticationapp;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.oytuntekesin.authenticationapp.business.GlycoBusiness;

public class BaseActivity extends AppCompatActivity {
    FirebaseFirestore _db;
    FirebaseAuth _auth;
    Context _context;
    GlycoBusiness _glycoBusiness;
    public BaseActivity(){
        _auth = FirebaseAuth.getInstance();
        _db = FirebaseFirestore.getInstance();
        _glycoBusiness = new GlycoBusiness();
    }
}
