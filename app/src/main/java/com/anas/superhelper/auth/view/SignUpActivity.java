package com.anas.superhelper.auth.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.anas.superhelper.R;

public class SignUpActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        FragmentManager manager = getSupportFragmentManager();

        manager.beginTransaction().add(R.id.frameLayout,new SignUpFirstPageFragment(),"sign_up_second_page_fragment")
                .addToBackStack(null).commit();

    }
}