package com.anas.superhelper.interfaces;

public interface Observer<T> {
    void onObserve(int event, T eventMessage);

}
