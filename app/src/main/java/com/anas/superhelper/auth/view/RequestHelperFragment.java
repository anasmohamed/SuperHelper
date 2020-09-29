package com.anas.superhelper.auth.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.anas.superhelper.R;
import com.anas.superhelper.auth.models.RequestHelper;
import com.anas.superhelper.auth.viewmodels.RequestHelperViewModel;
import com.anas.superhelper.auth.viewmodels.SignUpViewModel;

public class RequestHelperFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    String[] whoIsTheHelpFor = {"Individual Male", "Individual Female", "Couple"};
    String[] whatYouNeedHelpWith = {"read book", "crossing the street", "old man helping"};
    Spinner whoIsTheHelpForSpinner, whatYouNeedHelpWithSpinner;
    EditText relevantTagsET, requestTitleET, requestDetailsET;
    String whoIsTheHelpForText, whatYouNeedHelpWithText;
    Button getLocationBtn;
    RequestHelperViewModel requestHelperViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_helper, container, false);
        relevantTagsET = (EditText) view.findViewById(R.id.relevant_tags_et);
        requestTitleET = (EditText) view.findViewById(R.id.request_title_et);
        requestDetailsET = (EditText) view.findViewById(R.id.request_details_et);
        whoIsTheHelpForSpinner = (Spinner) view.findViewById(R.id.how_is_the_help_for_spinner);
        whatYouNeedHelpWithSpinner = (Spinner) view.findViewById(R.id.what_you_need_help_with_sppiner);
        getLocationBtn = (Button) view.findViewById(R.id.getLocationBtn);
        whoIsTheHelpForSpinner.setOnItemSelectedListener(this);
        whatYouNeedHelpWithSpinner.setOnItemSelectedListener(this);
        requestHelperViewModel = ViewModelProviders.of(this).get(RequestHelperViewModel.class);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter arrayAdapterWhoIsTheHelpFor = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, whoIsTheHelpFor);
        arrayAdapterWhoIsTheHelpFor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter arrayAdapterWhatYouNeedHelpWith = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, whatYouNeedHelpWith);
        arrayAdapterWhatYouNeedHelpWith.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        whatYouNeedHelpWithSpinner.setAdapter(arrayAdapterWhatYouNeedHelpWith);
        whoIsTheHelpForSpinner.setAdapter(arrayAdapterWhoIsTheHelpFor);


        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestHelper requestHelper = new RequestHelper();
                requestHelper.setLatitude(getArguments().getString(""));
                requestHelper.setLatitude(getArguments().getString(""));
                requestHelper.setRelevantTags(relevantTagsET.getText().toString());
                requestHelper.setRequestTitle(requestTitleET.getText().toString());
                requestHelper.setRequestDetails(requestDetailsET.getText().toString());
                requestHelper.setWhatYouNeedHelpWith(whatYouNeedHelpWithText);
                requestHelper.setWhoIsTheHelpFor(whoIsTheHelpForText);
                requestHelperViewModel.insertHelperRequestData(requestHelper);
                startActivity(new Intent(getActivity(), MapsFragment.class));
            }
        });
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.how_is_the_help_for_spinner) {
            whoIsTheHelpForText = whoIsTheHelpFor[position];
        }
        if (parent.getId() == R.id.what_you_need_help_with_sppiner) {
            whatYouNeedHelpWithText = whatYouNeedHelpWith[position];
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}