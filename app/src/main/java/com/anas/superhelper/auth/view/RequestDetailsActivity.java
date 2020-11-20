package com.anas.superhelper.auth.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anas.superhelper.OfferRecycleAdapter;
import com.anas.superhelper.R;
import com.anas.superhelper.RequestRecycleAdapter;
import com.anas.superhelper.auth.models.Offer;
import com.anas.superhelper.auth.viewmodels.RequestHelperViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RequestDetailsActivity extends AppCompatActivity {
    String receiverUID;
    int itemIndex;
    List<String> keyList = new ArrayList<>();
    Unbinder unbinder;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private RequestHelperViewModel requestHelperViewModel;


    @BindView(R.id.request_helper_offers_recycleView)
    RecyclerView offersRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);
        unbinder = ButterKnife.bind(this);

        requestHelperViewModel = ViewModelProviders.of(this).get(RequestHelperViewModel.class);
        itemIndex = getIntent().getExtras().getInt("uid");
        requestHelperViewModel.getKeysList(this::getKeyList);

        offersRecycleView.setLayoutManager(new LinearLayoutManager(RequestDetailsActivity.this));
        requestHelperViewModel.getOffersList(
                listLiveData -> offersRecycleView.setAdapter(new OfferRecycleAdapter(this,
                                 listLiveData.getValue(),
                                (clickedRequest) -> {

                                }
                        )
                )

        );



    }

    void getKeyList(List<String> keyList) {
        this.keyList = keyList;
        requestHelperViewModel.getSpecificValueFromRequest(this::getReceiverUID, "userId", keyList.get(itemIndex));

    }

    @OnClick(R.id.add_offer_btn)
    void onAddOfferBtnClick() {
        showAddItemDialog();
    }

    void getReceiverUID(String uid) {
        this.receiverUID = uid;
    }

    private void showAddItemDialog() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_offer_dialog, null);
        final EditText offerDetailsET = (EditText) dialogView.findViewById(R.id.offer_details_et);
        final EditText hourPrice = (EditText) dialogView.findViewById(R.id.hour_price_et);

        Button offerHelpBtn = (Button) dialogView.findViewById(R.id.offer_help_btn);
        Button cancelBtn = (Button) dialogView.findViewById(R.id.cancel_btn);


        offerHelpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Offer offer = new Offer();
                offer.setOfferDetails(offerDetailsET.getText().toString());
                offer.setHourPrice(hourPrice.getText().toString());
                offer.setSender(firebaseUser.getUid());
                offer.setOfferTime( new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));
                offer.setOfferDate(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));
                offer.setReceiver(receiverUID);
                requestHelperViewModel.insertOffer(offer, keyList.get(itemIndex));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}