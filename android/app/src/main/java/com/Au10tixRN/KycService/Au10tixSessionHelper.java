package com.Au10tixRN.KycService;

import com.facebook.react.bridge.Promise;

public class Au10tixSessionHelper {
    private Promise promise;

    public void setPromise(Promise promise) {
        this.promise = promise;
    }

    public void sendResult(String result) {
        if (promise != null) {
            promise.resolve(result);
        }
    }
}
