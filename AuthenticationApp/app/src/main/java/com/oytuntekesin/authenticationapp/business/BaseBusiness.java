package com.oytuntekesin.authenticationapp.business;

import android.content.Context;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.oytuntekesin.authenticationapp.dto.User;

import java.util.List;

public class BaseBusiness {
    public FirebaseAuth _auth;
    public FirebaseFirestore _db;
    public Context _context;
    User _logonUser;
    public BaseBusiness(){
        _auth = FirebaseAuth.getInstance();
        _db = FirebaseFirestore.getInstance();
    }
}
