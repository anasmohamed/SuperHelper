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

import com.anas.superhelper.MainActivity;
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
    int check = 0;
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
                startActivity(new Intent(RequestHelperFragment.this, MainActivity.class));
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
        whoIsTheHelpForSpinner.setOnItemSelectedListener(this);
        whatYouNeedHelpWithSpinner.setOnItemSelectedListener(this);

        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RequestHelperFragment.this, MapsFragment.class));
            }
        });
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (++check > 1) {


        if (parent.getId() == R.id.how_is_the_help_for_spinner) {
            whoIsTheHelpForText = whoIsTheHelpFor[position];
        }
        if (parent.getId() == R.id.what_you_need_help_with_sppiner) {
            whatYouNeedHelpWithText = whatYouNeedHelpWith[position];
        }}else
        {  whoIsTheHelpForText = whoIsTheHelpFor[0];
            whatYouNeedHelpWithText = whatYouNeedHelpWith[0];

        }
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
            whoIsTheHelpForText = whoIsTheHelpFor[0];
            whatYouNeedHelpWithText = whatYouNeedHelpWith[0];

    }
}