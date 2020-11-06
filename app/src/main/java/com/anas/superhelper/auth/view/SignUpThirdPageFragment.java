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

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignUpThirdPageFragment extends Fragment {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", Pattern.CASE_INSENSITIVE);
    Button nextFragmentBtn;
    EditText passwordET,confirmPasswordET;
    User user;
    Bundle bundle;
    Fragment signUpDisabledPersonalInfoFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_sign_up_third_page, container, false);
        nextFragmentBtn = (Button)view.findViewById(R.id.next_btn_sign_up_third_page);
        passwordET = (EditText) view.findViewById(R.id.password_et_sign_up_third_page);
        confirmPasswordET = (EditText)view.findViewById(R.id.confirm_password_et_sign_up_third_page);
        signUpDisabledPersonalInfoFragment = new SignUpDisabledPersonalInfoFragment();
        bundle = new Bundle();
        user = getArguments().getParcelable("user");
        nextFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (passwordET.getText().toString().isEmpty()) {
//                    passwordET.setError("Field cannot be empty");
//                } else if (confirmPasswordET.getText().toString().isEmpty()) {
//                    confirmPasswordET.setError("Field cannot be empty");
//
//
//                } else if (confirmPasswordET.getText().toString().equals(passwordET.getText().toString())) {
//                    confirmPasswordET.setError("Fields must be equal");
//                    passwordET.setError("Fields must be equal");
//                } else if (!validate(passwordET.getText().toString())){
//                    passwordET.setError("enter valid email");
//
//                }
//                  else{
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, signUpDisabledPersonalInfoFragment);
                    fragmentTransaction.addToBackStack(null);
                    user.setPassword(passwordET.getText().toString());
                    bundle.putParcelable("user",user);
                    signUpDisabledPersonalInfoFragment.setArguments(bundle);
                    fragmentTransaction.commit();
//                }

            }
        });
        return view;
    }
    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}