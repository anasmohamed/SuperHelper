package com.anas.superhelper.auth.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.anas.superhelper.R;
import com.anas.superhelper.auth.models.User;


public class SignUpLastPageFragment extends Fragment {
    Button signUpBtn;
User user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_last_page, container, false);
        signUpBtn = (Button) view.findViewById(R.id.sign_up_btn_sign_up_last_page);
        user = getArguments().getParcelable("user");
        Log.i("user object", "onCreateView: "+ user.getFirstName());
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }
}