package com.anas.superhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.anas.superhelper.auth.models.User;
import com.anas.superhelper.auth.view.LoginActivity;
import com.anas.superhelper.auth.view.SignUpActivity;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener {
   Button loginBtn,signUpAsHelperBtn,signUpForHelpBtn;
   User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        loginBtn = (Button)findViewById(R.id.login_btn);
        signUpAsHelperBtn = (Button)findViewById(R.id.sign_up_as_helper_btn);
        signUpForHelpBtn = (Button)findViewById(R.id.sign_up_to_find_help);
        signUpForHelpBtn.setOnClickListener(this::onClick);
        signUpAsHelperBtn.setOnClickListener(this::onClick);
        loginBtn.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.sign_up_as_helper_btn:
                Intent helperIntent = new Intent(this,SignUpActivity.class);
                helperIntent.putExtra("userType","helper");
                startActivity(helperIntent);

                break;
            case R.id.sign_up_to_find_help:
                Intent disabledIntent = new Intent(this,SignUpActivity.class);
                disabledIntent.putExtra("userType","disabled");

                startActivity(disabledIntent);

                break;
        }

    }
}