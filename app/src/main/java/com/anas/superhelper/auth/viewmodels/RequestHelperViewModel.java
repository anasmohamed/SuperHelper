package com.anas.superhelper.auth.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anas.superhelper.auth.models.Offer;
import com.anas.superhelper.auth.models.RequestHelper;
import com.anas.superhelper.auth.repository.RequestHelperRepository;

import java.util.List;
import java.util.function.Consumer;

public class RequestHelperViewModel extends ViewModel {
    public LiveData<RequestHelper> requestHelperLiveData;
    private RequestHelperRepository requestHelperRepository;
    private LiveData<List<RequestHelper>> requestHelperLiveDataList;
    private LiveData<List<Offer>> offerLiveDataList;

    public RequestHelperViewModel() {
        super();
        if (requestHelperRepository == null) {
            requestHelperRepository = new RequestHelperRepository();
        }
    }

    public LiveData<List<RequestHelper>> getRequestHelperLiveDataList() {
        return requestHelperLiveDataList;
    }

    public void insertHelperRequestData(RequestHelper requestHelper) {
        requestHelperRepository.insertHelperRequestData(requestHelper);
    }

    public void getSpecificValue(Consumer<String> returnedValue, String neededValue) {
        requestHelperRepository.getSpecificValue(returnedValue, neededValue);
    }

    public void getSpecificValueFromRequest(Consumer<String> returnedValue, String neededValue, String index) {
        requestHelperRepository.getSpecificValueFromRequest(returnedValue, neededValue, index);
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

    public void insertOffer(Offer offer, String key) {
        requestHelperRepository.insertOffer(offer, key);
    }

    public void getKeysList(Consumer<List<String>> returnedKeysList) {
        requestHelperRepository.getKeysList(returnedKeysList);

    }
    public void getOffersKeysList(String requestKey,Consumer<List<String>> returnedKeysList) {
        requestHelperRepository.getOffersKeys(requestKey,returnedKeysList);

    }
    public void updateOffersStatus(String requestKey,List<String>offersKeyList,String thisOfferKey)
    {
        requestHelperRepository.updateOfferStatus(requestKey,offersKeyList,thisOfferKey);
    }
    public void getSpecificValueFromOffers(String requestKey,String offerKey,String neededValue,Consumer<String>retrunedValue){
        requestHelperRepository.getSpecificValueFromOffers(requestKey,offerKey,neededValue,retrunedValue);
    }
    public void getOffersList(String key,Consumer<LiveData<List<Offer>>> returnedOffersLiveData)
    {
        requestHelperRepository.getOffers(key,offers -> {
            offerLiveDataList = new MutableLiveData<>(offers);

            returnedOffersLiveData.accept(offerLiveDataList);
                }
        );

    }

}
