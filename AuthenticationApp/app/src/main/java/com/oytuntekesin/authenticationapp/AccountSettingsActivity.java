package com.oytuntekesin.authenticationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.oytuntekesin.authenticationapp.dto.User;

import java.util.List;

public class AccountSettingsActivity extends BaseActivity{
    User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        _context = AccountSettingsActivity.this;

        EditText editTextCurrentPassword = findViewById(R.id.editTextCurrentPassword);
        EditText editTextNewPassword = findViewById(R.id.editTextNewPassword);
        EditText editTextVerifyPassword = findViewById(R.id.editTextVerifyPassword);

        Button buttonChangePassword = findViewById(R.id.buttonChangePassword);

        try {
            buttonChangePassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _db.collection("USER_TABLE").whereEqualTo("user_ID", _auth.getCurrentUser().getUid()).get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    if (!queryDocumentSnapshots.isEmpty()) {
                                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                        for (DocumentSnapshot d : list) {
                                            user = d.toObject(User.class);
                                        }
                                        Toast.makeText(_context,"Endişeye mahal yok endişeye", Toast.LENGTH_SHORT);
                                        if (user.getPASSWORD().equals(editTextCurrentPassword.getText().toString())){
                                            user.setPASSWORD(editTextNewPassword.getText().toString());
                                            _db.collection("USER_TABLE").document(user.getUSER_ID()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    _auth.getCurrentUser().updatePassword(user.getPASSWORD()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            Toast.makeText(_context, "Düzenlendi.", Toast.LENGTH_LONG).show();
                                                            Intent gecisYap = new Intent(getApplicationContext(), LoginActivity.class);
                                                            startActivity(gecisYap);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(_context, "Hata: " + e.getMessage(), Toast.LENGTH_LONG).show();

                                                        }
                                                    });

                                                }
                                            });
                                        }else{
                                            Toast.makeText(_context, "Mevcut şifre hatalı.", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(_context, "Hata : " + e.getMessage(), Toast.LENGTH_LONG).show();

                                }
                            });
                }
            });
        }catch (Exception ex){
            Toast.makeText(_context, "Hata : " + ex.getMessage(), Toast.LENGTH_LONG).show();

        }


    }
}
