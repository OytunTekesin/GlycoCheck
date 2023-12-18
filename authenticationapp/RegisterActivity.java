package com.oytuntekesin.authenticationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.oytuntekesin.authenticationapp.business.UserBusiness;

public class RegisterActivity extends AppCompatActivity {

    EditText txtUsername, txtEmail, txtPassword, txtPasswordCheck;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtUsername = findViewById(R.id.user_name);
        txtEmail = findViewById(R.id.email);
        txtPassword = findViewById(R.id.pasword);
        txtPasswordCheck = findViewById(R.id.pasword_again);

        btnRegister = findViewById(R.id.register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserBusiness userBusiness = new UserBusiness(view.getContext());
                String message =  userBusiness.CheckUserRegister(txtUsername.getText().toString(), txtEmail.getText().toString(), txtPassword.getText().toString(), txtPasswordCheck.getText().toString());
                if (message == null){
                    userBusiness.Register(txtUsername.getText().toString(), txtEmail.getText().toString(), txtPassword.getText().toString());
                }else {
                    Toast.makeText(view.getContext().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}