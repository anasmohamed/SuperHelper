package com.anas.superhelper.auth.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.anas.superhelper.R;

public class RequestHelperFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    String[] whoIsTheHelpFor = { "Individual Male", "Individual Female", "Couple"};
    String[] whatYouNeedHelpWith = {"read book","crossing the street","old man helping"};
    Spinner whoIsTheHelpForSpinner,whatYouNeedHelpWithSpinner;
    EditText relevantTagsET,requestTitleET,requestDetailsET;
    String whoIsTheHelpForText,whatYouNeedHelpWithText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_helper, container, false);
        relevantTagsET = (EditText)view.findViewById(R.id.relevant_tags_et);
        requestTitleET = (EditText)view.findViewById(R.id.request_title_et);
        requestDetailsET = (EditText)view.findViewById(R.id.request_details_et);
        whoIsTheHelpForSpinner = (Spinner)view.findViewById(R.id.how_is_the_help_for_spinner);
        whatYouNeedHelpWithSpinner = (Spinner)view.findViewById(R.id.what_you_need_help_with_sppiner);
        whoIsTheHelpForSpinner.setOnItemClickListener(this::onItemSelected);
        whatYouNeedHelpWithSpinner.setOnItemClickListener(this::onItemSelected);
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter arrayAdapterWhoIsTheHelpFor = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,whoIsTheHelpFor);
        arrayAdapterWhoIsTheHelpFor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter arrayAdapterWhatYouNeedHelpWith = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,whatYouNeedHelpWith);
        arrayAdapterWhatYouNeedHelpWith.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        whatYouNeedHelpWithSpinner.setAdapter(arrayAdapterWhatYouNeedHelpWith);
        whoIsTheHelpForSpinner.setAdapter(arrayAdapterWhoIsTheHelpFor);
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.how_is_the_help_for_spinner)
        {
            whoIsTheHelpForText = whoIsTheHelpFor[position];
        }
        if(parent.getId() == R.id.what_you_need_help_with_sppiner)
        {
            whatYouNeedHelpWithText = whatYouNeedHelpWith[position];
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}