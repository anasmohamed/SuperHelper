package com.anas.superhelper.auth.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.anas.superhelper.R;
import com.anas.superhelper.auth.models.User;
import com.hootsuite.nachos.NachoTextView;


public class SignUpDisabledPersonalInfoFragment extends Fragment {
    EditText addressEditText, jobEditText, disabledTypeEditText;
    NachoTextView interestsTextView;
    Button nextBtn;
    Fragment signUpLastFragment;
    Bundle bundle;
    User user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_disabled_personal_info, container, false);
        addressEditText = (EditText) view.findViewById(R.id.address_et);
        jobEditText = (EditText) view.findViewById(R.id.job_et);
        disabledTypeEditText = (EditText) view.findViewById(R.id.disabled_type_et);
        interestsTextView = (NachoTextView) view.findViewById(R.id.interests_text_view);
        nextBtn = (Button) view.findViewById(R.id.next_btn_disabled_personal_info);

        signUpLastFragment = new SignUpLastPageFragment();
        bundle = new Bundle();
        user = getArguments().getParcelable("user");

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addressEditText.getText().toString().isEmpty()) {
                    addressEditText.setError("Field cannot be empty");
                } else if (jobEditText.getText().toString().isEmpty()) {
                    jobEditText.setError("Field cannot be empty");
                } else if (disabledTypeEditText.getText().toString().isEmpty()) {
                    disabledTypeEditText.setError("Field cannot be empty");
                }else{
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, signUpLastFragment);
                    fragmentTransaction.addToBackStack(null);
                    user.setJob(jobEditText.getText().toString());
                    user.setAddress(addressEditText.getText().toString());
                    user.setDisabledType(disabledTypeEditText.getText().toString());
                    user.setInterests(interestsTextView.getText().toString().isEmpty() ? interestsTextView.getText().toString() : "");
                    bundle.putParcelable("user",user);
                    signUpLastFragment.setArguments(bundle);
                    fragmentTransaction.commit();
                }
            }
        });
        return view;
    }
}