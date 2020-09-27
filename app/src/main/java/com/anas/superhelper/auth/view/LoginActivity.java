package com.anas.superhelper.auth.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.anas.superhelper.ProfileActivity;
import com.anas.superhelper.R;
import com.anas.superhelper.auth.viewmodels.LoginViewModel;
import com.anas.superhelper.auth.viewmodels.SignUpViewModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    Button loginBtn;
    EditText emailET, passwordET;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = (Button) findViewById(R.id.login_btn);
        emailET = (EditText) findViewById(R.id.email_et_login_view);
        passwordET = (EditText) findViewById(R.id.password_et_login_view);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailET.getText().toString().isEmpty()) {
                    emailET.setError("Field cannot be empty");

                } else if (!validateEmail(emailET.getText().toString())) {
                    emailET.setError("enter valid email");

                } else if (passwordET.getText().toString().isEmpty()) {
                    passwordET.setError("enter valid password");

                } else {
                    loginViewModel.login(emailET.getText().toString(), passwordET.getText().toString());
                    loginViewModel.authenticatedUserLiveData.observe(LoginActivity.this, authenticatedUser -> {
                        if (!authenticatedUser.getEmail().isEmpty()) {
                            startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                        }
                    });
                }
            }
        });

    }

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }


}