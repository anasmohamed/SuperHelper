package com.anas.superhelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anas.superhelper.auth.models.RequestHelper;
import com.anas.superhelper.auth.view.RequestDetailsActivity;
import com.anas.superhelper.auth.view.RequestHelperFragment;
import com.anas.superhelper.auth.viewmodels.RequestHelperViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeFragment extends Fragment {

    RequestHelperViewModel requestHelperViewModel;
    @BindView(R.id.request_helper_recycleView)
    RecyclerView requestHelperRecyclerView;
    @BindView(R.id.create_new_request_text_view)
    TextView createNewRequestTextView;
    @BindView(R.id.no_requests_found_text_view)
    TextView noRequestsFoundTextView;
    String userType;

    @BindView(R.id.create_new_request_btn)
    FloatingActionButton createNewRequestBtn;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        requestHelperViewModel = ViewModelProviders.of(this).get(RequestHelperViewModel.class);
        requestHelperViewModel.getSpecificValue(this::getUserType,"userType");
        requestHelperRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        requestHelperViewModel.getRequests(true,
                listLiveData -> requestHelperRecyclerView.setAdapter(new RequestRecycleAdapter(getContext(),
                                listLiveData.getValue(),
                                (clickedRequest) -> {
                                    Intent intent = new Intent(getActivity(),RequestDetailsActivity.class);
                                    intent.putExtra("latitude",clickedRequest.getLatitude());
                                    intent.putExtra("longitude",clickedRequest.getLongitude());

                                    intent.putExtra("tags",clickedRequest.getRelevantTags().toString().isEmpty() ?  clickedRequest.getRelevantTags() : "");
                                    intent.putExtra("title",clickedRequest.getRequestTitle());
                                    intent.putExtra("details",clickedRequest.getRequestDetails());
                                    intent.putExtra("helpWith",clickedRequest.getWhatYouNeedHelpWith());
                                    intent.putExtra("helpFor",clickedRequest.getWhoIsTheHelpFor());
                                    intent.putExtra("uid",clickedRequest.getUid()) ;

                                    startActivity(intent);
                                }
                        )
                )

        );

        return view;
    }

    void getUserType(String userType) {
        this.userType = userType;
        if(userType.equalsIgnoreCase("helper"))
        {
            createNewRequestBtn.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.create_new_request_btn})
    void onCreateNewRequestBtnClick() {
        startActivity(new Intent(getActivity(), RequestHelperFragment.class));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}