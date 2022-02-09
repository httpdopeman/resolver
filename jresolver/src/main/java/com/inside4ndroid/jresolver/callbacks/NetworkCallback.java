package com.inside4ndroid.jresolver.callbacks;

public interface NetworkCallback {
    void onSuccess(String result);
    void onError(String result);
}