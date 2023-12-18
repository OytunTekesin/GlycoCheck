package com.oytuntekesin.authenticationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.oytuntekesin.authenticationapp.business.UserBusiness;

public class LoginActivity extends AppCompatActivity {

    private EditText txtEmail, txtPassword;
    Button btnLogin;
    TextView sign,forgot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET},100);
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) finishAffinity();
        }


        final UserBusiness userBusiness = new UserBusiness(getApplicationContext());
        txtEmail = findViewById(R.id.email);
        txtPassword = findViewById(R.id.pasword);

        forgot = (TextView) findViewById(R.id.forgot_password);
        sign = (TextView) findViewById(R.id.sign_in);
        btnLogin    =   (Button) findViewById(R.id.log_in);

        if (userBusiness.getLoginUser() != null){
            Intent gecisYap = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(gecisYap);
        }


        //Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = userBusiness.CheckUserLogin(txtEmail.getText().toString(), txtPassword.getText().toString());
                if (message == null){
                    userBusiness.Login(txtEmail.getText().toString(), txtPassword.getText().toString());
                }else {
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gecisYap = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(gecisYap);
            }
        });
    }

}