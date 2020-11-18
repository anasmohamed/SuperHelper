package com.anas.superhelper.auth.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Fragment signUpFirstPageFragment = new SignUpFirstPageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("userType",getIntent().getExtras().getString("userType"));
        signUpFirstPageFragment.setArguments(bundle);
        manager.beginTransaction().add(R.id.frameLayout,signUpFirstPageFragment,"sign_up_second_page_fragment")
                .addToBackStack(null).commit();

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}