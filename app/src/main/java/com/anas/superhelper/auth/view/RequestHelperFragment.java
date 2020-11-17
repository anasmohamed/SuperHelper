package com.anas.superhelper.auth.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.anas.superhelper.R;
import com.anas.superhelper.auth.models.RequestHelper;
import com.anas.superhelper.auth.viewmodels.RequestHelperViewModel;

import static android.content.Context.MODE_PRIVATE;

public class RequestHelperFragment extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] whoIsTheHelpFor = {"Individual Male", "Individual Female", "Couple"};
    String[] whatYouNeedHelpWith = {"read book", "crossing the street", "old man helping"};
    Spinner whoIsTheHelpForSpinner, whatYouNeedHelpWithSpinner;
    EditText relevantTagsET, requestTitleET, requestDetailsET;
    String whoIsTheHelpForText, whatYouNeedHelpWithText;
    Button getLocationBtn, sendHelperRequest;
    RequestHelperViewModel requestHelperViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_request_helper);
        relevantTagsET = (EditText) findViewById(R.id.relevant_tags_et);
        requestTitleET = (EditText) findViewById(R.id.request_title_et);
        requestDetailsET = (EditText) findViewById(R.id.request_details_et);
        whoIsTheHelpForSpinner = (Spinner) findViewById(R.id.how_is_the_help_for_spinner);
        whatYouNeedHelpWithSpinner = (Spinner) findViewById(R.id.what_you_need_help_with_spinner);
        getLocationBtn = (Button) findViewById(R.id.getLocationBtn);
        whoIsTheHelpForSpinner.setOnItemSelectedListener(this);
        whatYouNeedHelpWithSpinner.setOnItemSelectedListener(this);
        requestHelperViewModel = ViewModelProviders.of(this).get(RequestHelperViewModel.class);
        sendHelperRequest = (Button) findViewById(R.id.send_request_helper_btn);
        sendHelperRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestHelper requestHelper = new RequestHelper();
                SharedPreferences prefs = getSharedPreferences("location", MODE_PRIVATE);
                String name = prefs.getString("latitude", "");//"No name defined" is the default value.
                String idName = prefs.getString("longitude", ""); //0 is the default value.
                requestHelper.setLatitude(name);
                requestHelper.setLongitude(idName);
                requestHelper.setRelevantTags(relevantTagsET.getText().toString());
                requestHelper.setRequestTitle(requestTitleET.getText().toString());
                requestHelper.setRequestDetails(requestDetailsET.getText().toString());
                requestHelper.setWhatYouNeedHelpWith(whatYouNeedHelpWithText);
                requestHelper.setWhoIsTheHelpFor(whoIsTheHelpForText);
                requestHelperViewModel.insertHelperRequestData(requestHelper);

            }
        });
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter arrayAdapterWhoIsTheHelpFor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, whoIsTheHelpFor);
        arrayAdapterWhoIsTheHelpFor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter arrayAdapterWhatYouNeedHelpWith = new ArrayAdapter(this, android.R.layout.simple_spinner_item, whatYouNeedHelpWith);
        arrayAdapterWhatYouNeedHelpWith.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        whatYouNeedHelpWithSpinner.setAdapter(arrayAdapterWhatYouNeedHelpWith);
        whoIsTheHelpForSpinner.setAdapter(arrayAdapterWhoIsTheHelpFor);


        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RequestHelperFragment.this, MapsFragment.class));
            }
        });
    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_request_helper, container, false);
//        relevantTagsET = (EditText) view.findViewById(R.id.relevant_tags_et);
//        requestTitleET = (EditText) view.findViewById(R.id.request_title_et);
//        requestDetailsET = (EditText) view.findViewById(R.id.request_details_et);
//        whoIsTheHelpForSpinner = (Spinner) view.findViewById(R.id.how_is_the_help_for_spinner);
//        whatYouNeedHelpWithSpinner = (Spinner) view.findViewById(R.id.what_you_need_help_with_sppiner);
//        getLocationBtn = (Button) view.findViewById(R.id.getLocationBtn);
//        whoIsTheHelpForSpinner.setOnItemSelectedListener(this);
//        whatYouNeedHelpWithSpinner.setOnItemSelectedListener(this);
//        requestHelperViewModel = ViewModelProviders.of(this).get(RequestHelperViewModel.class);
//        sendHelperRequest = (Button) view.findViewById(R.id.send_request_helper_btn);
//        sendHelperRequest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RequestHelper requestHelper = new RequestHelper();
//                SharedPreferences prefs = getActivity().getSharedPreferences("location", MODE_PRIVATE);
//                String name = prefs.getString("latitude", "");//"No name defined" is the default value.
//                String idName = prefs.getString("longitude", ""); //0 is the default value.
//                requestHelper.setLatitude(name);
//                requestHelper.setLongitude(idName);
//                requestHelper.setRelevantTags(relevantTagsET.getText().toString());
//                requestHelper.setRequestTitle(requestTitleET.getText().toString());
//                requestHelper.setRequestDetails(requestDetailsET.getText().toString());
//                requestHelper.setWhatYouNeedHelpWith(whatYouNeedHelpWithText);
//                requestHelper.setWhoIsTheHelpFor(whoIsTheHelpForText);
//                requestHelperViewModel.insertHelperRequestData(requestHelper);
//
//            }
//        });
//        //Creating the ArrayAdapter instance having the country list
//        ArrayAdapter arrayAdapterWhoIsTheHelpFor = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, whoIsTheHelpFor);
//        arrayAdapterWhoIsTheHelpFor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        ArrayAdapter arrayAdapterWhatYouNeedHelpWith = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, whatYouNeedHelpWith);
//        arrayAdapterWhatYouNeedHelpWith.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        //Setting the ArrayAdapter data on the Spinner
//        whatYouNeedHelpWithSpinner.setAdapter(arrayAdapterWhatYouNeedHelpWith);
//        whoIsTheHelpForSpinner.setAdapter(arrayAdapterWhoIsTheHelpFor);
//
//
//        getLocationBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                startActivity(new Intent(getActivity(), MapsFragment.class));
//            }
//        });
//        return view;
//    }

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