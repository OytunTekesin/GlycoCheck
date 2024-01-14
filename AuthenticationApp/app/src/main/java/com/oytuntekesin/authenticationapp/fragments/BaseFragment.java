package com.oytuntekesin.authenticationapp.fragments;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.oytuntekesin.authenticationapp.business.BaseBusiness;
import com.oytuntekesin.authenticationapp.business.ExerciseBusiness;
import com.oytuntekesin.authenticationapp.business.GlycoBusiness;
import com.oytuntekesin.authenticationapp.business.NutritionBusiness;
import com.oytuntekesin.authenticationapp.business.UserBusiness;
import com.oytuntekesin.authenticationapp.dto.User;

import java.util.List;

public class BaseFragment extends Fragment {
    public FirebaseFirestore _db;
    public FirebaseAuth _auth;
    public Context _context;
    public GlycoBusiness _glycoBusiness;
    public ExerciseBusiness _exerciseBusiness;
    public UserBusiness _userBusiness;
    public NutritionBusiness _nutritionBusiness;
    public BaseFragment(){
        _db = FirebaseFirestore.getInstance();
        _auth = FirebaseAuth.getInstance();
        _glycoBusiness = new GlycoBusiness();
        _exerciseBusiness = new ExerciseBusiness();
        _userBusiness = new UserBusiness();
        _nutritionBusiness = new NutritionBusiness();
    }
}
