package com.anas.superhelper.auth.view;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.anas.superhelper.R;
import com.anas.superhelper.auth.models.User;
import com.anas.superhelper.auth.viewmodels.SignUpViewModel;

import java.util.Calendar;


public class SignUpLastPageFragment extends Fragment {
    Button signUpBtn;
    private SignUpViewModel signUpViewModel;
    EditText phoneNumberET, dateET;
    User user;
    private RadioGroup radioGenderGroup;
    private RadioButton radioButton;
    private int mYear, mMonth, mDay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_last_page, container, false);
        signUpBtn = (Button) view.findViewById(R.id.sign_up_btn_sign_up_last_page);
        phoneNumberET = (EditText) view.findViewById(R.id.phone_number_et_sign_up_last_page);
        dateET = (EditText) view.findViewById(R.id.date_of_birth_sign_up_last_page);
        radioGenderGroup = (RadioGroup) view.findViewById(R.id.gender_radio_group_sign_up_last_page);

        dateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                int selectedId = radioGenderGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) view.findViewById(selectedId);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                dateET.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        user = getArguments().getParcelable("user");
        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
        Log.i("user object", "onCreateView: " + user.getFirstName());
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneNumberET.getText().toString().isEmpty()) {
                    phoneNumberET.setError("Field cannot be empty");
                } else if (dateET.getText().toString().isEmpty()) {
                    dateET.setError("Field cannot be empty");


                } else {
                    user.setDate(dateET.getText().toString());
                    user.setPhone(phoneNumberET.getText().toString());
                    user.setGender(radioButton.getText().toString());
                    signUpViewModel.signUp(user);
                }
            }
        });
        return view;
    }
}