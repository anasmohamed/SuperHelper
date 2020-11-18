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

public class SignUpFirstPageFragment extends Fragment {

    Button nextBtn;
    EditText firstNameET, lastNameEt;
    Fragment signUpSecondFragment;
    Bundle bundle;
    User user = new User();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_first_page, container, false);
        nextBtn = (Button) view.findViewById(R.id.next_btn_sign_up_first_page);
        firstNameET = (EditText) view.findViewById(R.id.first_name_et_sign_up_first_page);
        lastNameEt = (EditText) view.findViewById(R.id.last_name_et_sign_up_first_page);
        signUpSecondFragment = new SignUpSecondPageFragment();
        bundle = new Bundle();
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstNameET.getText().toString().isEmpty()) {
                    firstNameET.setError("Field cannot be empty");
                }
               else if(lastNameEt.getText().toString().isEmpty())
                {                    lastNameEt.setError("Field cannot be empty");


                }else{
                user.setUserType(getArguments().getString("userType"));
                user.setFirstName(firstNameET.getText().toString());
                user.setLastName(lastNameEt.getText().toString());
                bundle.putParcelable("user",user);

                signUpSecondFragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout,signUpSecondFragment);

                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }}
        });
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().finish();
    }
}