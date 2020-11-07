package com.anas.superhelper.auth.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.anas.superhelper.R;
import com.anas.superhelper.androidchipbubbletext.ChipBubbleText;
import com.anas.superhelper.auth.models.User;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.tylersuehr.chips.ChipsInputLayout;


public class SignUpDisabledPersonalInfoFragment extends Fragment {
    EditText addressEditText, jobEditText, disabledTypeEditText;
    MultiAutoCompleteTextView interestsEditText;

    Button nextBtn;
    Fragment signUpLastFragment;
    Bundle bundle;
    User user;
    String[] values = {"coutinho","suarez","messi","ronaldo","silva","aguero"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_disabled_personal_info, container, false);
        addressEditText = (EditText) view.findViewById(R.id.address_et);
        jobEditText = (EditText) view.findViewById(R.id.job_et);
        disabledTypeEditText = (EditText) view.findViewById(R.id.disabled_type_et);
        interestsEditText = (MultiAutoCompleteTextView) view.findViewById(R.id.interests_text_view);
        nextBtn = (Button) view.findViewById(R.id.next_btn_disabled_personal_info);
        ChipBubbleText cp = new ChipBubbleText(getActivity(), interestsEditText, values, 1);
        cp.setChipColor("#9999FF");
        cp.setChipTextSize(20);
        cp.initialize();
        signUpLastFragment = new SignUpLastPageFragment();
        bundle = new Bundle();
        user = getArguments().getParcelable("user");

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("chips",interestsEditText.getText().toString());

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
                    user.setInterests(interestsEditText.getText().toString().isEmpty() ? "" :interestsEditText.getText().toString());
                    bundle.putParcelable("user",user);
                    signUpLastFragment.setArguments(bundle);
                    fragmentTransaction.commit();
                }
            }
        });
        return view;
    }
}