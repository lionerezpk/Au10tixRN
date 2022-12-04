package com.Au10tixRN.KycService;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

import com.au10tix.sdk.core.Au10xCore;
import com.au10tix.sdk.core.comm.SessionCallback;
import com.au10tix.sdk.protocol.Au10Update;
import com.au10tix.sdk.protocol.FeatureSessionError;
import com.au10tix.sdk.protocol.FeatureSessionResult;
import com.au10tix.smartDocument.SmartDocumentFeatureManager;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactContext;

public class KYCServiceSDCView extends FrameLayout implements LifecycleOwner {
    private Lifecycle lifecycle;
    private Au10xCore au10xCore;
//    private PreviewView previewView;

    private ReactContext getReactContext() {
        return (ReactContext) this.getContext();
    }

    private void init() {
        this.setBackgroundColor(Color.RED);
        lifecycle = new LifecycleRegistry(this);
        this.getReactContext().addLifecycleEventListener(new LifecycleEventListener() {
            @Override
            public void onHostResume() {
            }

            @Override
            public void onHostPause() {

            }

            @Override
            public void onHostDestroy() {

            }
        });
        au10xCore = Au10xCore.getInstance(this.getContext());
//        previewView = new PreviewView(this.getContext());
//        this.addView(previewView);
//        previewView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    public KYCServiceSDCView(@NonNull Context context) {
        super(context);
        init();
    }

    public KYCServiceSDCView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KYCServiceSDCView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        SmartDocumentFeatureManager smartDocumentFeatureManager = new SmartDocumentFeatureManager(this.getReactContext(), this);
        au10xCore.startSession(smartDocumentFeatureManager, this, new SessionCallback() {
            @Override
            public void onSessionResult(FeatureSessionResult featureSessionResult) {

            }

            @Override
            public void onSessionError(FeatureSessionError featureSessionError) {

            }

            @Override
            public void onSessionUpdate(Au10Update au10Update) {

            }
        });
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return this.lifecycle;
    }
}
