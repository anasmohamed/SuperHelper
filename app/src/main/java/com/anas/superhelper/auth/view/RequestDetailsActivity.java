package com.anas.superhelper.auth.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.anas.superhelper.R;
import com.anas.superhelper.auth.models.RequestHelper;

import butterknife.OnClick;

public class RequestDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);
      String requestHelper = getIntent().getExtras().getString("title");
        Log.i("returnedRequest",requestHelper);
    }

    @OnClick(R.id.add_offer_btn)
    void onAddOfferBtnClick()
    {
   showAddItemDialog();
    }

    private void showAddItemDialog() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_offer_dialog, null);
        TextView titleTextView = (TextView) dialogView.findViewById(R.id.textView);
        final EditText firstEditText = (EditText) dialogView.findViewById(R.id.first_edit_text);
        final EditText secondEditText = (EditText) dialogView.findViewById(R.id.second_edit_text);

        titleTextView.setText("Update " + title);
        Button updateBtn = (Button) dialogView.findViewById(R.id.buttonSubmit);
        Button cancelBtn = (Button) dialogView.findViewById(R.id.buttonCancel);
        switch (title) {
            case "name":
                secondEditText.setVisibility(View.VISIBLE);
                firstEditText.setHint(R.string.first_name);
                secondEditText.setHint(R.string.second_name);
                break;
            case "mobile number":
                firstEditText.setHint(R.string.mobile);
                break;
        }


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (title) {
                    case "name":

                        break;
                    case "mobile number":
                        break;

                }
                dialogBuilder.dismiss();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DO SOMETHINGS
                dialogBuilder.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }
}