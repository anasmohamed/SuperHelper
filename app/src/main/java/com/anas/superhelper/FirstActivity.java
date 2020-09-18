package com.anas.superhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class FirstActivity extends AppCompatActivity {
   Button loginBtn,signUpAsHelperBtn,signUpForHelpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        loginBtn = (Button)findViewById(R.id.login_btn);
        signUpAsHelperBtn = (Button)findViewById(R.id.sign_up_as_helper_btn);
        signUpForHelpBtn = (Button)findViewById(R.id.sign_up_to_find_help);
    }
}