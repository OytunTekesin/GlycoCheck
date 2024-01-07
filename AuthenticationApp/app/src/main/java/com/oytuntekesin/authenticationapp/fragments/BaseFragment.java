package com.oytuntekesin.authenticationapp.fragments;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.oytuntekesin.authenticationapp.business.BaseBusiness;

public class BaseFragment extends Fragment {
    FirebaseFirestore _db;
    FirebaseAuth _auth;
    Context _context;
    public BaseFragment(){
          _db = FirebaseFirestore.getInstance();
        _auth = FirebaseAuth.getInstance();
    }
}
