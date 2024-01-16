package com.oytuntekesin.authenticationapp.business;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.oytuntekesin.authenticationapp.LoginActivity;
import com.oytuntekesin.authenticationapp.MainActivity;
import com.oytuntekesin.authenticationapp.R;
import com.oytuntekesin.authenticationapp.dto.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserBusiness extends BaseBusiness {
    public UserBusiness(){
    }
    public void  getUserListForSpinner(Context context, Spinner spinner){
        List<User> users = new ArrayList<User>();
        _db.collection("USER_TABLE").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                User cd = d.toObject(User.class);
                                users.add(cd);
                            }
                            ArrayAdapter<User> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, users);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            spinner.setAdapter(adapter);
                        } else {
                            Toast.makeText(context, "Veri kaydı bulunamadı!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // if we do not get any data or any error we are displaying
                        // a toast message that we do not get any data
                        Toast.makeText(context, "Veri kaydı bulunamadı!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public FirebaseUser getLoginUser() {
        return _auth.getCurrentUser();
    }
    public String CheckUserLogin(Context context,String email, String password) {
        if (email.isEmpty() || password.isEmpty()){
            return context.getString(R.string.auth_data_empty);
        }
        if (!email.contains("@") || !email.contains(".") || email.indexOf("@") > email.lastIndexOf(".")){
            return context.getString(R.string.auth_email_error);
        }
        return null;
    }

    public void Login(Context context,String email, String password){

        _auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String CheckUserRegister(Context context,String username, String email, String password, String passwordCheck) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || passwordCheck.isEmpty()){
            return context.getString(R.string.auth_data_empty);
        }
        if (!email.contains("@") || !email.contains(".") || email.indexOf("@") > email.lastIndexOf(".")){
            return  context.getString(R.string.auth_email_error);
        }
        if (!password.equals(passwordCheck)){
            return context.getString(R.string.register_password_validate_error);
        }
        return null;
    }
    public void Register(Context context,final User user){
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.loading_data));
        progressDialog.show();
        _auth.createUserWithEmailAndPassword(user.getEMAIL(), user.getPASSWORD()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                user.setUSER_ID(authResult.getUser().getUid());
                user.setUSER_ROLE("USER");
                _db.collection("USER_TABLE").document(user.getUSER_ID()).set(user);

                progressDialog.dismiss();
                //Email doğrulama
                Toast.makeText(context.getApplicationContext(), context.getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public String CheckResetPassword(Context context,String email) {
        if (email.isEmpty()){
            return context.getString(R.string.auth_data_empty);
        }
        if (!email.contains("@") || !email.contains(".") || email.indexOf("@") > email.lastIndexOf(".")){
            return  context.getString(R.string.auth_email_error);
        }
        return null;
    }

    public void ResetPassword(Context context, String email) {
        _auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, context.getString(R.string.reset_password_message), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context.getApplicationContext(), LoginActivity.class);
                context.startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
