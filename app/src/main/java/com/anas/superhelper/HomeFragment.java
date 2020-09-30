package com.anas.superhelper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anas.superhelper.auth.viewmodels.LoginViewModel;
import com.anas.superhelper.auth.viewmodels.RequestHelperViewModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment {

    RequestHelperViewModel requestHelperViewModel;
    @BindView(R.id.request_helper_recycleView)
    RecyclerView requestHelperRecyclerView;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        requestHelperViewModel = ViewModelProviders.of(this).get(RequestHelperViewModel.class);
        requestHelperRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        requestHelperViewModel.getRequests(true,
                listLiveData -> requestHelperRecyclerView.setAdapter(new RequestRecycleAdapter(getContext(),
                                listLiveData.getValue(),
                                (clickedRequest) -> Toast.makeText(getContext(), clickedRequest.getRequestTitle(), Toast.LENGTH_SHORT).show()
                        )
                )
        );
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}