package com.anas.superhelper.auth.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.anas.superhelper.R;
import com.anas.superhelper.auth.models.Offer;
import com.anas.superhelper.auth.viewmodels.RequestHelperViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.OnClick;

public class RequestDetailsActivity extends AppCompatActivity {
private RequestHelperViewModel requestHelperViewModel;
String receiverUID;
    int itemIndex;
FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);
        requestHelperViewModel = ViewModelProviders.of(this).get(RequestHelperViewModel.class);
         itemIndex = getIntent().getExtras().getInt("uid");
        requestHelperViewModel.getSpecificValueFromRequest(this::getReceiverUID,"userId",itemIndex);
        Log.i("returnedRequest", itemIndex+"");
    }

    @OnClick(R.id.add_offer_btn)
    void onAddOfferBtnClick() {
        showAddItemDialog();
    }

    void getReceiverUID(String uid){
this.receiverUID = uid;
    }
    private void showAddItemDialog() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_offer_dialog, null);
        final EditText offerDetailsET = (EditText) dialogView.findViewById(R.id.offer_details_et);
        final EditText hourPrice = (EditText) dialogView.findViewById(R.id.hour_price_et);

        Button offerHelpBtn = (Button) dialogView.findViewById(R.id.offer_help_btn);
        Button cancelBtn = (Button) dialogView.findViewById(R.id.buttonCancel);


        offerHelpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Offer offer = new Offer();
                offer.setOfferDetails(offerDetailsET.getText().toString());
                offer.setHourPrice(hourPrice.getText().toString());
                offer.setSender(firebaseUser.getUid());
                offer.setReceiver(receiverUID);
requestHelperViewModel.insertOffer(offer,itemIndex);
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