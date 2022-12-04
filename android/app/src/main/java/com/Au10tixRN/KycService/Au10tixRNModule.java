package com.Au10tixRN.KycService;

import androidx.annotation.NonNull;

import com.au10tix.sdk.core.Au10xCore;
import com.Au10tixRN.MainApplication;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import android.content.Intent;
import android.util.Log;

import java.io.File;

public class Au10tixRNModule extends ReactContextBaseJavaModule {
    private static String JWT = "";
    static Au10tixSessionHelper sessionHelper = new Au10tixSessionHelper();

    Au10tixRNModule(ReactApplicationContext context) {
        super(context);
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public void setJWT(String jwt) {
        JWT = jwt.replaceAll(System.getProperty("line.separator"), "");
//        cleanJWT = cleanJWT.replace(" ", "");
//        JWT = cleanJWT;
    }

    @ReactMethod()
    public void startSDCSession(Promise promise) {
        sessionHelper.setPromise(promise);
        Intent intent = new Intent(MainApplication.mActivity, SDCActivity.class);
        intent.putExtra("jwt", JWT);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        MainApplication.mActivity.startActivity(intent);
    }

    @ReactMethod()
    public void startLivenessSession(Promise promise) {
        sessionHelper.setPromise(promise);
        Intent intent = new Intent(MainApplication.mActivity, LivenessActivity.class);
        intent.putExtra("jwt", JWT);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        MainApplication.mActivity.startActivity(intent);
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public void cleanSession() {
        String frontIdFileName = "front_image.png";
        String livenessFileName = "liveness.png";
        deleteFile(frontIdFileName);
        deleteFile(livenessFileName);
    }

    @NonNull
    @Override
    public String getName() {
        return "Au10tixRNModule";
    }

    private void deleteFile(String fileName) {
        String dirPath = getReactApplicationContext().getFilesDir().getAbsolutePath();
        File file = new File(dirPath + fileName);
        if (file.exists()) {
            file.delete();
        }
    }
}
