package com.anas.superhelper.auth.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anas.superhelper.auth.models.RequestHelper;
import com.anas.superhelper.auth.models.Offer;
import com.anas.superhelper.auth.repository.RequestHelperRepository;

import java.util.List;
import java.util.function.Consumer;

public class RequestHelperViewModel extends ViewModel {
    private RequestHelperRepository requestHelperRepository;
    public LiveData<RequestHelper> requestHelperLiveData;
    private LiveData<List<RequestHelper>> requestHelperLiveDataList;

    public LiveData<List<RequestHelper>> getRequestHelperLiveDataList() {
        return requestHelperLiveDataList;
    }

    public RequestHelperViewModel() {
        super();
        if (requestHelperRepository == null) {
            requestHelperRepository = new RequestHelperRepository();
        }
    }

    public void insertHelperRequestData(RequestHelper requestHelper) {
        requestHelperRepository.insertHelperRequestData(requestHelper);
    }
    public void getSpecificValue(Consumer<String> returnedValue,String neededValue)
    {
        requestHelperRepository.getSpecificValue(returnedValue,neededValue);
    }
    public void getSpecificValueFromRequest(Consumer<String> returnedValue,String neededValue,int index)
    {
        requestHelperRepository.getSpecificValueFromRequest(returnedValue,neededValue,index);
    }
//    public  getRequests() {
//        if (requestHelperLiveDataList == null) {
//            requestHelperRepository.getRequests(requestHelpers -> {
//                requestHelperLiveDataList = new MutableLiveData<>(requestHelpers);
//            });
//        }
//    }
    public void getRequests(boolean forceUpdate, Consumer<LiveData<List<RequestHelper>>> returnedLiveData) {
        if (requestHelperLiveDataList == null || forceUpdate) {
            requestHelperRepository.getRequests(requestHelpers -> {
                requestHelperLiveDataList = new MutableLiveData<>(requestHelpers);
                returnedLiveData.accept(requestHelperLiveDataList);
            });
        } else {
            returnedLiveData.accept(requestHelperLiveDataList);
        }
    }
public void insertOffer(Offer offer,int index)
{
    requestHelperRepository.insertOffer(offer,index);
}
}
