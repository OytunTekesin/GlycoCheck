package com.oytuntekesin.authenticationapp;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.oytuntekesin.authenticationapp.business.ExerciseBusiness;
import com.oytuntekesin.authenticationapp.business.GlycoBusiness;
import com.oytuntekesin.authenticationapp.business.NutritionBusiness;
import com.oytuntekesin.authenticationapp.business.UserBusiness;
import com.oytuntekesin.authenticationapp.dto.User;

import java.util.List;

public class BaseActivity extends AppCompatActivity {
    FirebaseFirestore _db;
    FirebaseAuth _auth;
    Context _context;
    UserBusiness _userBusiness;
    GlycoBusiness _glycoBusiness;
    ExerciseBusiness _exerciseBusiness;
    NutritionBusiness _nutritionBusiness;
    User _logonUser;
    public BaseActivity(){
        _auth = FirebaseAuth.getInstance();
        _db = FirebaseFirestore.getInstance();
        _exerciseBusiness = new ExerciseBusiness();
        _glycoBusiness = new GlycoBusiness();
        _nutritionBusiness = new NutritionBusiness();
        _userBusiness = new UserBusiness();
    }
}
