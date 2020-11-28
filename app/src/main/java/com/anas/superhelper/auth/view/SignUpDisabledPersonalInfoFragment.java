package com.anas.superhelper.auth.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.anas.superhelper.R;
import com.anas.superhelper.androidchipbubbletext.ChipBubbleText;
import com.anas.superhelper.auth.models.User;


public class SignUpDisabledPersonalInfoFragment extends Fragment implements
        AdapterView.OnItemSelectedListener {
    EditText addressEditText, jobEditText;
    MultiAutoCompleteTextView interestsEditText;
    Spinner disabledTypeSpinner;
    Button nextBtn;
    Fragment signUpLastFragment;
    Bundle bundle;
    User user;
    String[] values = {"القراءه", "الرياضة", "مشاهدة الافلام", "البرمجة"};
    String[] country = {"ضعف الرؤية.", "الصم", "اعافة جسدية"};
TextView disableTypeTitle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_disabled_personal_info, container, false);
        addressEditText = (EditText) view.findViewById(R.id.address_et);
        jobEditText = (EditText) view.findViewById(R.id.job_et);
        disabledTypeSpinner = (Spinner) view.findViewById(R.id.disable_type_spinner);
        disableTypeTitle = (TextView)view.findViewById(R.id.disable_type_title);
        interestsEditText = (MultiAutoCompleteTextView) view.findViewById(R.id.interests_text_view);
        nextBtn = (Button) view.findViewById(R.id.next_btn_disabled_personal_info);
        disabledTypeSpinner.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter disabledTypeSpinnerArrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, country);
        disabledTypeSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        disabledTypeSpinner.setAdapter(disabledTypeSpinnerArrayAdapter);
        ChipBubbleText cp = new ChipBubbleText(getActivity(), interestsEditText, values, 1);
        cp.setChipColor("#9999FF");
        cp.setChipTextSize(20);
        cp.initialize();
        signUpLastFragment = new SignUpLastPageFragment();
        bundle = new Bundle();
        user = getArguments().getParcelable("user");
if(user.getUserType().equalsIgnoreCase("helper"))
{
    disabledTypeSpinner.setVisibility(View.GONE);
disableTypeTitle.setVisibility(View.GONE);
}
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("chips", interestsEditText.getText().toString());

                if (addressEditText.getText().toString().isEmpty()) {
                    addressEditText.setError("Field cannot be empty");
                } else if (jobEditText.getText().toString().isEmpty()) {
                    jobEditText.setError("Field cannot be empty");
                } else {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, signUpLastFragment);
                    fragmentTransaction.addToBackStack(null);
                    user.setJob(jobEditText.getText().toString());
                    user.setAddress(addressEditText.getText().toString());

                    user.setInterests(interestsEditText.getText().toString().isEmpty() ? "" : interestsEditText.getText().toString());
                    bundle.putParcelable("user", user);
                    signUpLastFragment.setArguments(bundle);
                    fragmentTransaction.commit();
                }
            }
        });
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        user.setDisabledType(country[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        user.setDisabledType(country[0]);

    }
}