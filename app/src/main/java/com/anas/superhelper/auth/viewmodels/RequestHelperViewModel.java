package com.anas.superhelper.auth.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.anas.superhelper.auth.models.RequestHelper;
import com.anas.superhelper.auth.models.User;
import com.anas.superhelper.auth.repository.LoginRepository;
import com.anas.superhelper.auth.repository.RequestHelperRepository;

public class RequestHelperViewModel extends ViewModel {
    private RequestHelperRepository requestHelperRepository;
    public LiveData<RequestHelper> requestHelperLiveData;
    public RequestHelperViewModel() {
        super();
        if(requestHelperRepository == null){
            requestHelperRepository = new RequestHelperRepository();
        }
    }

    public void insertHelperRequestData(RequestHelper requestHelper) {
           requestHelperRepository.insertHelperRequestData(requestHelper);
    }
}
