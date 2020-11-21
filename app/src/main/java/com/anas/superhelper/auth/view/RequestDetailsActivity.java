package com.anas.superhelper.auth.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anas.superhelper.OfferRecycleAdapter;
import com.anas.superhelper.R;
import com.anas.superhelper.RequestRecycleAdapter;
import com.anas.superhelper.auth.models.Offer;
import com.anas.superhelper.auth.viewmodels.RequestHelperViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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

public class RequestDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
    String receiverUID;
    int itemIndex;
    List<String> keyList = new ArrayList<>();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private RequestHelperViewModel requestHelperViewModel;
    String  profileImageURL,offerSenderName;

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

    GoogleMap map;
    Unbinder unbinder;

    String longitude,latitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);
        unbinder = ButterKnife.bind(this);

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

//        SupportMapFragment map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView));
       mMap.onCreate(savedInstanceState);
        mMap.getMapAsync(this);

    }
    void getProfileImageURL(String profileImageURL){
        this.profileImageURL = profileImageURL;

    }
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        double mLat = Double.valueOf(latitude);
        double mLon = Double.valueOf(longitude);
        LatLng mylocation = new LatLng(mLat, mLon);
        map.addMarker(new MarkerOptions().position(mylocation).title("Help Location")).showInfoWindow();
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(mylocation,15));
    }
    void getFirstName(String lastName){
        this.offerSenderName = lastName;

    }
    void getLastName(String lastName)
    {
        this.offerSenderName.concat(" "+ lastName);

    }


    void getKeyList(List<String> keyList) {
        this.keyList = keyList;
        requestHelperViewModel.getSpecificValueFromRequest(this::getReceiverUID, "userId", keyList.get(itemIndex));
        requestHelperViewModel.getSpecificValue(this::getProfileImageURL,"profileImage");
        requestHelperViewModel.getSpecificValue(this::getFirstName,"firstName");
        requestHelperViewModel.getSpecificValue(this::getLastName,"lastName");

        requestHelperViewModel.getOffersList(keyList.get(itemIndex),
                listLiveData -> offersRecycleView.setAdapter(new OfferRecycleAdapter(this,
                                listLiveData.getValue(),
                                (clickedRequest) -> {

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
                offer.setSenderProfileImageURl(profileImageURL);
                offer.setSenderName(offerSenderName);
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