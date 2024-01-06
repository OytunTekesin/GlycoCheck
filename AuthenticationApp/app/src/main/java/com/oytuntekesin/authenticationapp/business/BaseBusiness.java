package com.oytuntekesin.authenticationapp.business;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class BaseBusiness {
    public FirebaseAuth _auth;
    public FirebaseFirestore _db;
    public FirebaseUser _logonUser;
    public Context _context;
    public BaseBusiness(){
        _auth = FirebaseAuth.getInstance();
        _db = FirebaseFirestore.getInstance();
        _logonUser = _auth.getCurrentUser();
    }
    public FirebaseUser getLoginUser() {
        return _auth.getCurrentUser();
    }
}
