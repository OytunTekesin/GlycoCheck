package com.oytuntekesin.authenticationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.oytuntekesin.authenticationapp.business.BaseBusiness;
import com.oytuntekesin.authenticationapp.business.UserBusiness;
import com.oytuntekesin.authenticationapp.dto.User;

public class RegisterActivity extends BaseActivity {

    EditText txtUsername, txtEmail, txtPassword, txtPasswordCheck;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        _context = RegisterActivity.this;

        txtUsername = findViewById(R.id.user_name);
        txtEmail = findViewById(R.id.email);
        txtPassword = findViewById(R.id.pasword);
        txtPasswordCheck = findViewById(R.id.pasword_again);

        btnRegister = findViewById(R.id.register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserBusiness userBusiness = new UserBusiness();
                String message =  userBusiness.CheckUserRegister(_context, txtUsername.getText().toString(), txtEmail.getText().toString(), txtPassword.getText().toString(), txtPasswordCheck.getText().toString());
                if (message == null){
                    User user = new User();
                    user.setUSER_ADI(txtUsername.getText().toString());
                    user.setEMAIL(txtEmail.getText().toString());
                    user.setPASSWORD(txtPassword.getText().toString());
                    userBusiness.Register(_context, user);
                }else {
                    Toast.makeText(view.getContext().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}