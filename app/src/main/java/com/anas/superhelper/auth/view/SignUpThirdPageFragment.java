package com.anas.superhelper.auth.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.anas.superhelper.R;
import com.anas.superhelper.auth.models.User;


public class SignUpThirdPageFragment extends Fragment {

    Button nextFragmentBtn;
    EditText passwordET,confirmPasswordET;
    User user;
    Bundle bundle;
    Fragment signUpLastFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_sign_up_third_page, container, false);
        nextFragmentBtn = (Button)view.findViewById(R.id.next_btn_sign_up_third_page);
        passwordET = (EditText) view.findViewById(R.id.password_et_sign_up_third_page);
        confirmPasswordET = (EditText)view.findViewById(R.id.confirm_password_et_sign_up_third_page);
        signUpLastFragment = new SignUpLastPageFragment();
        bundle = new Bundle();
        user = getArguments().getParcelable("user");
        nextFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, signUpLastFragment);
                fragmentTransaction.addToBackStack(null);
                user.setPassword(passwordET.getText().toString());
                bundle.putParcelable("user",user);
                signUpLastFragment.setArguments(bundle);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}