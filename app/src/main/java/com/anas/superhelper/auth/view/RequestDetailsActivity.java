package com.anas.superhelper.auth.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anas.superhelper.OfferRecycleAdapter;
import com.anas.superhelper.R;
import com.anas.superhelper.auth.models.Offer;
import com.anas.superhelper.auth.viewmodels.RequestHelperViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RequestDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
    String receiverUID;
    int itemIndex;
    List<String> keyList = new ArrayList<>();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String profileImageURL, offerSenderName;
    @BindView(R.id.mapView)
    MapView mMap;
    @BindView(R.id.request_for_who_value)
    TextView requestForWhoValue;
    @BindView(R.id.request_for_what_value)
    TextView requestForWhatValue;
    @BindView(R.id.request_title_value)
    TextView requestTitleValue;
    @BindView(R.id.request_details_value)
    TextView requestDetails;
    @BindView(R.id.request_helper_offers_recycleView)
    RecyclerView offersRecycleView;
    @BindView(R.id.add_offer_btn)
    Button addOfferBtn;


    GoogleMap map;
    Unbinder unbinder;
    String longitude, latitude;
    AlertDialog.Builder builder;
    List<String> offersKeysList;
    String phoneNumber;
    String offerSenderPhoneNumber;
    private RequestHelperViewModel requestHelperViewModel;
String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);
        unbinder = ButterKnife.bind(this);
        builder = new AlertDialog.Builder(this);

        requestHelperViewModel = ViewModelProviders.of(this).get(RequestHelperViewModel.class);
        itemIndex = getIntent().getExtras().getInt("uid");
        requestForWhatValue.setText(getIntent().getExtras().getString("helpWith"));
        requestForWhoValue.setText(getIntent().getExtras().getString("helpFor"));
        requestTitleValue.setText(getIntent().getExtras().getString("title"));
        latitude = getIntent().getExtras().getString("latitude");
        longitude = getIntent().getExtras().getString("longitude");

        requestHelperViewModel.getKeysList(this::getKeyList);
        requestDetails.setText(getIntent().getExtras().getString("details"));
        offersRecycleView.setLayoutManager(new LinearLayoutManager(RequestDetailsActivity.this));

        mMap.onCreate(savedInstanceState);
        mMap.getMapAsync(this);

    }

    void getProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;

    }

    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        double mLat = Double.valueOf(latitude);
        double mLon = Double.valueOf(longitude);
        LatLng mylocation = new LatLng(mLat, mLon);
        map.addMarker(new MarkerOptions().position(mylocation).title("موقع المساعدة المطلوبة")).showInfoWindow();
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(mylocation, 15));
    }

    void getFirstName(String lastName) {
        this.offerSenderName = lastName;

    }

    void getPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    void getLastName(String lastName) {
        this.offerSenderName.concat(" " + lastName);

    }

    void getUserType(String userType) {
        if (!userType.equalsIgnoreCase("helper") && addOfferBtn != null) {
            addOfferBtn.setVisibility(View.GONE);

        }

    }

    void getOffersKeyList(List<String> offersKeysList) {
        this.offersKeysList = offersKeysList;
        if(!offersKeysList.isEmpty()) {

            requestHelperViewModel.getSpecificValueFromOffers(keyList.get(itemIndex), offersKeysList.get(itemIndex), "senderPhoneNumber", this::getOfferSenderPhoneNumber);
            requestHelperViewModel.getSpecificValueFromOffers(keyList.get(itemIndex), offersKeysList.get(itemIndex), "sender", this::getOfferSenderId);
        }
    }
    void getOfferStatus(String status) {
       this.status = status;
        if (status.equalsIgnoreCase(getString(R.string.accept_status))) {
            builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);
            builder.setMessage(getString(R.string.offer_accepted ))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.done_btn), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                            dialog.cancel();
                        }
                    });

            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle(getString(R.string.offer_status));
            alert.show();
        }
    }


    void getOfferSenderId(String offerSenderId) {
        Log.i("offerId",offerSenderId);
        if (offerSenderId.equalsIgnoreCase(firebaseUser.getUid()) && addOfferBtn != null) {
            addOfferBtn.setEnabled(false);
            addOfferBtn.setText(R.string.already_add_offer);

            requestHelperViewModel.getSpecificValueFromOffers(keyList.get(itemIndex), offersKeysList.get(itemIndex), "status", this::getOfferStatus);

        }
    }

    void getOfferSenderPhoneNumber(String phoneNumber) {
        offerSenderPhoneNumber = phoneNumber;
        Log.i("offerSenderPhone", offerSenderPhoneNumber);

    }

    void getKeyList(List<String> keyList) {
        this.keyList = keyList;
        Log.d("keylistsize", "keyslist " + keyList.size());

        requestHelperViewModel.getSpecificValueFromRequest(this::getReceiverUID, "senderId", keyList.get(itemIndex));
        requestHelperViewModel.getSpecificValue(this::getProfileImageURL, "profileImageURL");
        requestHelperViewModel.getSpecificValue(this::getFirstName, "firstName");
        requestHelperViewModel.getSpecificValue(this::getLastName, "lastName");
        requestHelperViewModel.getSpecificValue(this::getUserType, "userType");
        requestHelperViewModel.getOffersKeysList(keyList.get(itemIndex), this::getOffersKeyList);
        requestHelperViewModel.getSpecificValue(this::getPhoneNumber, "phone");
        requestHelperViewModel.getOffersList(keyList.get(itemIndex),
                listLiveData -> offersRecycleView.setAdapter(new OfferRecycleAdapter(this,
                                listLiveData.getValue(),
                                (clickedRequest) -> {
                                    builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);
                                    builder.setMessage(getString(R.string.do_you_accept_this_offer))
                                            .setCancelable(false)
                                            .setPositiveButton(getString(R.string.accept), new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    requestHelperViewModel.updateOffersStatus(keyList.get(itemIndex), offersKeysList, offersKeysList.get(clickedRequest.getOfferNumberInTheList()));
                                                    showAddItemDialog(offerSenderPhoneNumber);
                                                }
                                            })
                                            .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    //  Action for 'NO' Button
                                                    dialog.cancel();

                                                }
                                            });
                                    //Creating dialog box
                                    AlertDialog alert = builder.create();
                                    //Setting the title manually
                                    alert.setTitle(getString(R.string.offer_accept));
                                    alert.show();
                                }
                        )
                )

        );

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
        final EditText offerDetailsET = dialogView.findViewById(R.id.offer_details_et);
        final EditText hourPrice = dialogView.findViewById(R.id.hour_price_et);

        Button offerHelpBtn = dialogView.findViewById(R.id.offer_help_btn);
        Button cancelBtn = dialogView.findViewById(R.id.cancel_btn);


        offerHelpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Offer offer = new Offer();
                offer.setOfferDetails(offerDetailsET.getText().toString());
                offer.setHourPrice(hourPrice.getText().toString());
                offer.setSender(firebaseUser.getUid());
                offer.setOfferTime(new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));
                offer.setOfferDate(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));
                offer.setReceiver(receiverUID);
                offer.setStatus("binding");
                offer.setSenderProfileImageURl(profileImageURL);
                offer.setSenderName(offerSenderName);
                offer.setSenderPhoneNumber(phoneNumber);
                requestHelperViewModel.insertOffer(offer, keyList.get(itemIndex));

                dialogBuilder.dismiss();
//                finish();
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

    private void showAddItemDialog(String title) {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.accept_offer_success_dialog, null);
        TextView phoneNumberTextView = dialogView.findViewById(R.id.call_this_number_value_tv);
        phoneNumberTextView.setText(title);
        Button updateBtn = dialogView.findViewById(R.id.call_btn);
        Button cancelBtn = dialogView.findViewById(R.id.cancel_btn);


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogBuilder.dismiss();
                                                                    finish();

            }
        });
//        cancelBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // DO SOMETHINGS
//                dialogBuilder.dismiss();
//            }
//        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }
}