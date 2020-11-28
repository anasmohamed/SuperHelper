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

public class SignUpSecondPageFragment extends Fragment {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    Button nextFragmentBtn;
    EditText emailET, confirmEmailET;
    Bundle bundle;
    Fragment signUpThirdFragment;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_second_page, container, false);
        nextFragmentBtn = (Button) view.findViewById(R.id.next_btn_sign_up_second_page);
        emailET = (EditText) view.findViewById(R.id.email_et_sign_up_second_page);
        confirmEmailET = (EditText) view.findViewById(R.id.confirm_email_et_sign_up_second_page);
        signUpThirdFragment = new SignUpThirdPageFragment();
        bundle = new Bundle();
        user = getArguments().getParcelable("user");
        nextFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailET.getText().toString().isEmpty()) {
                    emailET.setError("يجب ادخال هذه البيانات");
                } else if (confirmEmailET.getText().toString().isEmpty()) {
                    confirmEmailET.setError("يجب ادخال البيانات بشكل صحيح");


                } else if (!confirmEmailET.getText().toString().equals(emailET.getText().toString())) {
                    confirmEmailET.setError("يجب ادخال البيانات بشكل صحيح");
                    emailET.setError("البيانات يجب ان تكون متطابقة");
                } else if (!validate(emailET.getText().toString())){
                    emailET.setError("ادخل ايميل صحيح");

                }
                else {

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, signUpThirdFragment);
                    user.setEmail(emailET.getText().toString());
                    bundle.putParcelable("user", user);
                    signUpThirdFragment.setArguments(bundle);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });
        return view;
    }


    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}