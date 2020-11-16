package com.anas.superhelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.anas.superhelper.auth.view.RequestHelperFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new RequestHelperFragment());

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.home:
                   // toolbar.setTitle("Shop");
                    fragment = new HomeFragment();
                    loadFragment(fragment);

                    return true;
                case R.id.notification:
                  //  toolbar.setTitle("My Gifts");
                    return true;
                case R.id.account:
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
//                case R.id.navigation_cart:
//                    toolbar.setTitle("Cart");
//                    return true;
//                case R.id.navigation_profile:
//                    toolbar.setTitle("Profile");
//                    return true;
            }
            return false;
        }
    };
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(fragment instanceof RequestHelperFragment)
        {
            if(getIntent().hasExtra("latitude"))
            {
                Bundle bundle = new Bundle();
                bundle.putString("latitude",getIntent().getStringExtra("latitude"));
                bundle.putString("longitude",getIntent().getStringExtra("longitude"));
                fragment.setArguments(bundle);
            }
        }
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);}
    }
}