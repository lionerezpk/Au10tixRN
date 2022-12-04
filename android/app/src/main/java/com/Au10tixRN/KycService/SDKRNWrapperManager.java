package com.Au10tixRN.KycService;


import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

public class SDKRNWrapperManager extends SimpleViewManager<View> {
//    private ThemedReactContext context;
    private KYCServiceSDCView viewInstance;

    SDKRNWrapperManager() {}

    @NonNull
    @Override
    public String getName() {
        return "SDKRNWrapperManager";
    }

    @NonNull
    @Override
    protected View createViewInstance(@NonNull ThemedReactContext themedReactContext) {
        viewInstance = new KYCServiceSDCView(themedReactContext);
        viewInstance.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        return viewInstance;
    }
}
