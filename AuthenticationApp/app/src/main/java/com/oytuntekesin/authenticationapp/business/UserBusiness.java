package com.oytuntekesin.authenticationapp.business;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.oytuntekesin.authenticationapp.LoginActivity;
import com.oytuntekesin.authenticationapp.MainActivity;
import com.oytuntekesin.authenticationapp.R;

import java.util.HashMap;
import java.util.Map;

public class UserBusiness extends BaseBusiness {
    Context _context;
    public UserBusiness(Context context){
        _auth = FirebaseAuth.getInstance();
        _context = context;
    }

    public String CheckUserLogin(String email, String password) {
        if (email.isEmpty() || password.isEmpty()){
            return _context.getString(R.string.auth_data_empty);
        }
        if (!email.contains("@") || !email.contains(".") || email.indexOf("@") > email.lastIndexOf(".")){
            return _context.getString(R.string.auth_email_error);
        }
        return null;
    }

    public void Login(String email, String password){

        _auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Intent intent = new Intent(_context.getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                _context.startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(_context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String CheckUserRegister(String username, String email, String password, String passwordCheck) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || passwordCheck.isEmpty()){
            return _context.getString(R.string.auth_data_empty);
        }
        if (!email.contains("@") || !email.contains(".") || email.indexOf("@") > email.lastIndexOf(".")){
            return  _context.getString(R.string.auth_email_error);
        }
        if (!password.equals(passwordCheck)){
            return _context.getString(R.string.register_password_validate_error);
        }
        return null;
    }
    public void Register(final String username, final String email, final String password){
        final ProgressDialog progressDialog = new ProgressDialog(_context);
        progressDialog.setMessage(_context.getString(R.string.loading_data));
        progressDialog.show();
        _auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                progressDialog.dismiss();
                //Email doğrulama
                Toast.makeText(_context.getApplicationContext(), _context.getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(_context, MainActivity.class);
                _context.startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(_context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public String CheckResetPassword(String email) {
        if (email.isEmpty()){
            return _context.getString(R.string.auth_data_empty);
        }
        if (!email.contains("@") || !email.contains(".") || email.indexOf("@") > email.lastIndexOf(".")){
            return  _context.getString(R.string.auth_email_error);
        }
        return null;
    }

    public void ResetPassword(String email) {
        _auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(_context, _context.getString(R.string.reset_password_message), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(_context.getApplicationContext(), LoginActivity.class);
                _context.startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(_context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
