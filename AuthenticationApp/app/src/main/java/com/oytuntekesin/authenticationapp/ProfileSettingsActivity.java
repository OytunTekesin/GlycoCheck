package com.oytuntekesin.authenticationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.oytuntekesin.authenticationapp.dto.User;

import java.util.List;

public class ProfileSettingsActivity extends BaseActivity{
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        _context = ProfileSettingsActivity.this;

        EditText editTextUsername = findViewById(R.id.editTextUsername);
        EditText editTextEmail = findViewById(R.id.editTextEmail);
        EditText editTextBoy = findViewById(R.id.editTextBoy);
        EditText editTextKilo = findViewById(R.id.editTextKilo);

        Button buttonSaveUserProfile = findViewById(R.id.buttonSaveUserProfile);
        user = new User();
        _db.collection("USER_TABLE").whereEqualTo("user_ID", _auth.getCurrentUser().getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                user = d.toObject(User.class);
                                editTextUsername.setText(user.getUSER_ADI());
                                editTextEmail.setText(user.getEMAIL());
                                editTextBoy.setText(user.getBOY());
                                editTextKilo.setText(user.getKILO());
                            }

                        }
                    }
                });

        buttonSaveUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _db.collection("USER_TABLE").document(user.getUSER_ID()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(_context, "Düzenlendi.", Toast.LENGTH_LONG).show();
                        Intent gecisYap = new Intent(getApplicationContext(), MainActivity.class);
                        gecisYap.putExtra("TAB", "3");
                        startActivity(gecisYap);
                    }
                });
            }
        });
    }
}
